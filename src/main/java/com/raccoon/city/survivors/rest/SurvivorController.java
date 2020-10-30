package com.raccoon.city.survivors.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.raccoon.city.survivors.model.entity.Bag;
import com.raccoon.city.survivors.model.entity.Supply;
import com.raccoon.city.survivors.model.entity.SupplyBag;
import com.raccoon.city.survivors.model.entity.Survivor;
import com.raccoon.city.survivors.model.repository.SurvivorRepository;
import com.raccoon.city.survivors.rest.dto.SupplyBagDTO;
import com.raccoon.city.survivors.rest.dto.SurvivorDTO;

@RestController
@RequestMapping("/api/umbrella")
public class SurvivorController {

	private final SurvivorRepository survivorRepository;
	
	@Autowired
	public SurvivorController(SurvivorRepository survivorRepository) {
		this.survivorRepository = survivorRepository;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody SurvivorDTO survivorDTO) {
		
		Survivor survivor = new Survivor();
		survivor.setName(survivorDTO.getName());
		survivor.setAge(survivorDTO.getAge());
		survivor.setGender(survivorDTO.getGender());
		survivor.setLastLocation("test");
		
		Bag bag = new Bag();
		List<SupplyBag> suppliesBag = new ArrayList<SupplyBag>();
		
		for (SupplyBagDTO dto : survivorDTO.getSupplies()) {
			Supply supply = new Supply();
			supply.setId(dto.getIdSupply());
			
			SupplyBag supplyBag = new SupplyBag();
			supplyBag.setBag(bag);
			supplyBag.setSupply(supply);
			supplyBag.setQuantity(dto.getQuantity());
			
			suppliesBag.add(supplyBag);
		}
		
		bag.setBlocked(false);
		bag.setSuppliesBag(suppliesBag);
		
		survivor.setBag(bag);
		survivorRepository.save(survivor);

	}
}
