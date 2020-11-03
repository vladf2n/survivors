package com.raccoon.city.survivors.model;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Location {

  private double lat;

  private double lng;

}