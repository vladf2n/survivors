package com.raccoon.city.survivors.rest;

import java.math.BigDecimal;
import java.util.Map;

import com.raccoon.city.survivors.dto.SingleStatisticsDTO;
import com.raccoon.city.survivors.dto.StatisticsDTO;
import com.raccoon.city.survivors.service.StatisticsService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/umbrella/statistics")
@RequiredArgsConstructor
public class StatisticsController {

  private final StatisticsService statisticsService;

  @GetMapping
  public StatisticsDTO getStatistics() {
    return statisticsService.getStatistics();
  }

  @GetMapping("/infected-percentage")
  public SingleStatisticsDTO<BigDecimal> infectedSurvivorsPercentage() {
    return statisticsService.getInfectedSurvivorsPercentage();
  }

  @GetMapping("/healthy-percentage")
  public SingleStatisticsDTO<BigDecimal> healthySurvivorPercentage() {
    return statisticsService.getHealthySurvivorsPercentage();
  }

  @GetMapping("/avg-resources")
  public Map<String, BigDecimal> averageSuppliesPerSurvivors() {
    return statisticsService.getAverageSuppliesPerSurvivor();
  }

  @GetMapping("/lost-points")
  public SingleStatisticsDTO<Long> lostPoints() {
    return statisticsService.getLostPoints();
  }
}