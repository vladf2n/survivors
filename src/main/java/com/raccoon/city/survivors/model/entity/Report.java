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
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_report")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_survivor")
	private Survivor infectedSurvivor;
	
//	@ManyToOne
//	@JoinColumn(name = "id_survivor")
//	private Survivor snitch;
}
