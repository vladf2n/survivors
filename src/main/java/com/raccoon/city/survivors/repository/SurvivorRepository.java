package com.raccoon.city.survivors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.raccoon.city.survivors.model.Survivor;

import java.util.Optional;

public interface SurvivorRepository extends JpaRepository<Survivor, Integer> {

  @Query("from Survivor s JOIN FETCH s.bag b JOIN FETCH b.supplyBags WHERE s.id = :id")
  Optional<Survivor> findByIdWithSupplies(@Param("id") Integer id);

  @Query("from Survivor s JOIN FETCH s.infectionReports ir WHERE s.id = :id")
  Optional<Survivor> findByIdWithInfectionReports(@Param("id") Integer id);
}
