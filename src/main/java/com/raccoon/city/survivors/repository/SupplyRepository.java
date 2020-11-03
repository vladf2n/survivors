package com.raccoon.city.survivors.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raccoon.city.survivors.model.Supply;

public interface SupplyRepository extends JpaRepository<Supply, Integer> {

}
