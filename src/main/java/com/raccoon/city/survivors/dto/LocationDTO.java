package com.raccoon.city.survivors.dto;

import javax.validation.constraints.NotNull;

import com.raccoon.city.survivors.model.Location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Location")
public class LocationDTO {

  @NotNull
  @ApiModelProperty(required = true)
  private double lat;

  @NotNull
  @ApiModelProperty(required = true)
  private double lng;

  public Location toEntity() {
    return new Location(lat, lng);
  }

  public static LocationDTO from(Location location) {
    return new LocationDTO(location.getLat(), location.getLng());
  }
}