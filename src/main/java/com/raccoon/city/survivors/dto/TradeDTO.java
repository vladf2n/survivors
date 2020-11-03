package com.raccoon.city.survivors.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.raccoon.city.survivors.config.Constants;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "Trade")
public class TradeDTO {

    @NotEmpty
    @Size(min = 2, max = 2, message = Constants.TRADE_PROPOSAL_SIZE_VALIDATION_ERROR)
    private List<TradeProposalDTO> proposals;

    public TradeProposalDTO firstProposal() {
        return proposals.get(0);
    }

    public TradeProposalDTO secondProposal() {
        return proposals.get(1);
    }

    public Set<Integer> allProposedItemsIds() {
        Set<Integer> proposedItemsIds = new HashSet<>();

        proposedItemsIds.addAll(firstProposal().proposedItemsIds());
        proposedItemsIds.addAll(secondProposal().proposedItemsIds());

        return proposedItemsIds;
    }

    public List<SupplyBagDTO> firstProposalItems() {
        return firstProposal().getProposedItems();
    }

    public List<SupplyBagDTO> secondProposalItems() {
        return secondProposal().getProposedItems();
    }
}