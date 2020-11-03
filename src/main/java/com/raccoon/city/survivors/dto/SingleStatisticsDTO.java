package com.raccoon.city.survivors.dto;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@ApiModel(value = "Single Statistic")
public class SingleStatisticsDTO<T> {
  public T data;
}
