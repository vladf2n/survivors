package com.raccoon.city.survivors.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Survivor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_survivor")
	private Integer id;
	
	@Column(name = "name", nullable = false, length = 150)
	private String name;
	
	@Column(name = "age", nullable = false)
	private Integer age;
	
	@Column(name = "gender", nullable = false, length = 10)
	private String gender;
	
	@Column(name = "last_location", nullable = false)
	private String lastLocation;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_bag")
	private Bag bag;
	
	@Column(name = "infected")
	private Boolean infected;
	
	@OneToMany(mappedBy = "infectedSurvivor")
	private List<Report> snitchers;
	
}
