package com.raccoon.city.survivors.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raccoon.city.survivors.model.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {

}
