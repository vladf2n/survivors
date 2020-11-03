package com.raccoon.city.survivors.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raccoon.city.survivors.model.Bag;
import com.raccoon.city.survivors.model.Survivor;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Survivor")
public class SurvivorDTO {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer id;

	@NotBlank
	private String name;

	@NotNull
	private Integer age;

	@NotBlank
	private String gender;

	private Boolean infected;

	@NotNull
	private LocationDTO lastLocation;

	@NotNull
	private List<SupplyBagDTO> supplies;

	public static SurvivorDTO from(Survivor survivor) {
		return SurvivorDTO.builder()
			.id(survivor.getId())
			.name(survivor.getName())
			.age(survivor.getAge())
			.infected(survivor.isInfected())
			.gender(survivor.getGender())
			.lastLocation(
				LocationDTO.from(survivor.getLastLocation())
			)
			.supplies(
				survivor.getSupplyBags().stream().map(SupplyBagDTO::from).collect(Collectors.toList())
			)
			.build();
	}

	public Survivor toEntity() {
		return Survivor.builder()
			.name(name)
			.age(age)
			.gender(gender)
			.infected(infected != null ? infected : false)
			.lastLocation(lastLocation.toEntity())
			.bag(
				Bag.builder()
					.supplyBags(
						supplies.stream().map(SupplyBagDTO::toEntity).collect(Collectors.toList())
					)
					.blocked(false)
					.build()
			)
			.build();
	}
}
