package com.raccoon.city.survivors.dto;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ApiModel(value = "Api Error")
public class ApiError {
  private Boolean error = true;

  private final Map<String, String> messages;
}