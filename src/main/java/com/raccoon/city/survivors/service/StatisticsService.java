package com.raccoon.city.survivors.service;

import java.math.BigDecimal;
import java.util.Map;

import com.raccoon.city.survivors.dao.StatisticsDAO;
import com.raccoon.city.survivors.dto.SingleStatisticsDTO;
import com.raccoon.city.survivors.dto.StatisticsDTO;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticsService {

  public final StatisticsDAO statisticsDAO;

  public StatisticsDTO getStatistics() {
    return StatisticsDTO.builder()
      .infected(statisticsDAO.getInfectedSurvivorsPercentage())
      .healthy(statisticsDAO.getHealthySurvivorsPercentage())
      .lostPoints(statisticsDAO.getLostPoints())
      .averageSupplies(statisticsDAO.getAverageSuppliesPerSurvivor())
      .build();
  }

  public SingleStatisticsDTO<BigDecimal> getInfectedSurvivorsPercentage() {
    return new SingleStatisticsDTO<>(statisticsDAO.getInfectedSurvivorsPercentage());
  }

  public SingleStatisticsDTO<BigDecimal> getHealthySurvivorsPercentage() {
    return new SingleStatisticsDTO<>(statisticsDAO.getHealthySurvivorsPercentage());
  }

  public Map<String, BigDecimal> getAverageSuppliesPerSurvivor() {
    return statisticsDAO.getAverageSuppliesPerSurvivor();
  }

  public SingleStatisticsDTO<Long> getLostPoints() {
    return new SingleStatisticsDTO<>(statisticsDAO.getLostPoints());
  }

}