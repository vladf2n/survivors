package com.raccoon.city.survivors.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raccoon.city.survivors.model.entity.Supply;

public interface SupplyRepository extends JpaRepository<Supply, Integer> {

}
