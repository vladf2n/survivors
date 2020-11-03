package com.raccoon.city.survivors.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Survivor {

	@Id
	@With
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private Integer age;

	private String gender;

	@Builder.Default
	@Getter(AccessLevel.NONE)
	private Boolean infected = Boolean.FALSE;

	@Embedded
	private Location lastLocation;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "bag_id")
	private Bag bag;

	@OneToMany(mappedBy = "target", cascade = CascadeType.ALL)
	private List<InfectionReport> infectionReports;

	public Boolean isInfected() {
		return infected;
	}

	public Boolean wasAlreadyReportedBy(Survivor survivor) {
		return infectionReports.stream()
			.anyMatch(infectionReport ->
				survivor.equals(infectionReport.getReporter())
			);
	}

	public Boolean timesReported(Integer times) {
		return infectionReports.size() >= times;
	}

	public List<SupplyBag> getSupplyBags() {
		return bag.getSupplyBags();
	}

	public Boolean ownsItemsIn(Bag otherBag) {
		return bag.containsAllItemsFrom(otherBag);
	}

	public void giveItems(Bag otherBag) {
		bag.removeItemsFrom(otherBag);
	}

	public void receiveItems(Bag otherBag) {
		bag.receiveItemsFrom(otherBag);
	}

	public void exchange(Bag takeBag, Bag receiveBag) {
		bag.removeItemsFrom(takeBag);
		bag.receiveItemsFrom(receiveBag);
	}
}
