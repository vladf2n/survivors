package com.raccoon.city.survivors.model.entity;

import javax.persistence.Column;
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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplyBag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_supply_bag")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_supply")
	private Supply supply;
	
	@ManyToOne()
	@JoinColumn(name = "id_bag")
	private Bag bag;
	
	@Column(name = "quantity", nullable = false)
	private Integer quantity;
}
