package com.raccoon.city.survivors.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raccoon.city.survivors.model.entity.Bag;

public interface BagRepository extends JpaRepository<Bag, Integer> {

}
