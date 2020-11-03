package com.raccoon.city.survivors.dto;

import java.math.BigDecimal;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@ApiModel(value = "Statistics")
public class StatisticsDTO {

    private BigDecimal infected;
    private BigDecimal healthy;
    private Map<String, BigDecimal> averageSupplies;
    private Long lostPoints;
}