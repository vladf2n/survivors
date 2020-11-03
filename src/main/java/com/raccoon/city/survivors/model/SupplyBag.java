package com.raccoon.city.survivors.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class SupplyBag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer quantity;

	@ManyToOne
	@JoinColumn(name = "supply_id")
	private Supply supply;

	@ManyToOne(optional = false)
	@JoinColumn(name = "bag_id")
	private Bag bag;

	public Integer totalPoints() {
		return quantity * supply.getPoints();
	}

	public void remove(Integer quantity) {
		this.quantity -= quantity;
	}

	public void add(Integer quantity) {
		this.quantity += quantity;
	}

	public static SupplyBag of(Integer id, Supply supply, Integer quantity) {
		return SupplyBag.builder()
			.id(id)
			.supply(supply)
			.quantity(quantity)
			.build();
	}

	public static SupplyBag of(Supply supply, Integer quantity) {
		return SupplyBag.builder()
			.supply(supply)
			.quantity(quantity)
			.build();
	}
}
