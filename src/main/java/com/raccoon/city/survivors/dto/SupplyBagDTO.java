package com.raccoon.city.survivors.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.raccoon.city.survivors.model.Supply;
import com.raccoon.city.survivors.model.SupplyBag;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"supplyId"})
@ApiModel(value = "Supply Bag")
public class SupplyBagDTO {

	@NotNull
	private Integer supplyId;

	@NotNull
	@Min(0)
	private Integer quantity;

	public SupplyBag toEntity() {
		return SupplyBag.of(new Supply().withId(this.supplyId), this.quantity);
	}

	public static SupplyBagDTO from(SupplyBag supplyBag) {
		return SupplyBagDTO.builder()
			.supplyId(supplyBag.getSupply().getId())
			.quantity(supplyBag.getQuantity())
			.build();
	}
}