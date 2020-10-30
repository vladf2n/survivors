package com.raccoon.city.survivors.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raccoon.city.survivors.model.entity.Survivor;

public interface SurvivorRepository extends JpaRepository<Survivor, Integer> {

}
