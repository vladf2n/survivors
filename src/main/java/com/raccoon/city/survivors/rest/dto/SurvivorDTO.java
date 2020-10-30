package com.raccoon.city.survivors.rest.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SurvivorDTO {

	@NotEmpty
	private String name;
	
	@NotEmpty
	private Integer age;
	
	@NotEmpty
	private String gender;
	
	private String lastLocation;
	
	private List<SupplyBagDTO> supplies;
	
}	
