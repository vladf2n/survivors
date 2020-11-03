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
@EqualsAndHashCode(of = { "id" })
public class InfectionReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "target_survivor_id")
	private Survivor target;

	@ManyToOne
	@JoinColumn(name = "reporter_survivor_id")
	private Survivor reporter;

	public static InfectionReport of(Survivor target, Survivor reporter) {
		return InfectionReport.builder()
			.target(target)
			.reporter(reporter)
			.build();
	}
}
