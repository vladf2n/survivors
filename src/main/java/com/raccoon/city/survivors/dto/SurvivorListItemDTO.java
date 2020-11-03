package com.raccoon.city.survivors.dto;

import com.raccoon.city.survivors.model.Survivor;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@ApiModel(value = "Survivor List Item")
public class SurvivorListItemDTO {

	private Integer id;
	private String name;
	private Integer age;
	private String gender;

	public static SurvivorListItemDTO from(Survivor survivor) {
		return SurvivorListItemDTO.builder()
			.id(survivor.getId())
			.name(survivor.getName())
			.age(survivor.getAge())
			.gender(survivor.getGender())
			.build();
	}
}
