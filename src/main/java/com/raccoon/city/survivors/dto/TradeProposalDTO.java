package com.raccoon.city.survivors.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "Trade Proposal")
public class TradeProposalDTO {

    @NotNull
    private Integer survivorId;

    @NotEmpty
    private List<SupplyBagDTO> proposedItems;

    public Set<Integer> proposedItemsIds() {
        return proposedItems.stream().map(SupplyBagDTO::getSupplyId).collect(Collectors.toSet());
    }
}
