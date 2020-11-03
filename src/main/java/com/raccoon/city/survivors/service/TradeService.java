package com.raccoon.city.survivors.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.raccoon.city.survivors.config.Constants;
import com.raccoon.city.survivors.dto.SupplyBagDTO;
import com.raccoon.city.survivors.dto.TradeDTO;
import com.raccoon.city.survivors.exception.NotFoundException;
import com.raccoon.city.survivors.exception.TradeException;
import com.raccoon.city.survivors.model.Bag;
import com.raccoon.city.survivors.model.Supply;
import com.raccoon.city.survivors.model.SupplyBag;
import com.raccoon.city.survivors.model.Survivor;
import com.raccoon.city.survivors.repository.SupplyRepository;
import com.raccoon.city.survivors.repository.SurvivorRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TradeService {

    private final SurvivorRepository survivorRepository;
    private final SupplyRepository supplyRepository;

    @Transactional
    public void trade(TradeDTO tradeDTO) {
        Survivor firstSurvivor = loadSurvivor(tradeDTO.firstProposal().getSurvivorId());
        validateSurvivor(firstSurvivor);

        Survivor secondSurvivor = loadSurvivor(tradeDTO.secondProposal().getSurvivorId());
        validateSurvivor(secondSurvivor);

        Map<Integer, Supply> supplyMap = loadInvolvedSupplies(tradeDTO.allProposedItemsIds());

        Bag firstProposal = proposalBagOf(tradeDTO.firstProposalItems(), supplyMap);
        Bag secondProposal = proposalBagOf(tradeDTO.secondProposalItems(), supplyMap);

        if (firstProposal.totalSupplyPoints() != secondProposal.totalSupplyPoints()) {
            log.error("Unbalanced points in both proposals");
            throw new TradeException(Constants.UNBALANCED_TRADE_ERROR);
        }

        Boolean isFirstProposalValid = firstSurvivor.ownsItemsIn(firstProposal);
        Boolean isSecondProposalValid = secondSurvivor.ownsItemsIn(secondProposal);

        if (!(isFirstProposalValid && isSecondProposalValid)) {
            log.error("Survivor without supplies to complete the trade");
            throw new TradeException(Constants.SHORT_SUPPLIES_ERROR);
        }

        firstSurvivor.exchange(firstProposal, secondProposal);
        secondSurvivor.exchange(secondProposal, firstProposal);

        survivorRepository.saveAll(Arrays.asList(new Survivor[] {firstSurvivor, secondSurvivor}));
    }

    private Survivor loadSurvivor(int survivorId) {
        return survivorRepository.findById(survivorId).orElseThrow(NotFoundException::new);
    }

    private void validateSurvivor(Survivor survivor) {
        if (survivor.isInfected()) {
            log.error("Infected survivor in trade");
            throw new TradeException(Constants.SURVIVOR_INFECTED_ERROR);
        }
    }

    private Bag proposalBagOf(List<SupplyBagDTO> proposal, Map<Integer, Supply> supplyMap) {
        return Bag.builder()
        .supplyBags(
            proposal.stream()
            .map(proposedItem -> {
                return SupplyBag.of(supplyMap.get(proposedItem.getSupplyId()), proposedItem.getQuantity());
            })
            .collect(Collectors.toList())
        ).build();
    }

    private Map<Integer, Supply> loadInvolvedSupplies(Set<Integer> supplyIds) {
        return supplyRepository.findAllById(supplyIds)
            .stream()
            .collect(
                Collectors.toMap(Supply::getId, supply -> supply)
            );
    }
}
