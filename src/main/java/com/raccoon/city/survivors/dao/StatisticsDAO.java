package com.raccoon.city.survivors.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StatisticsDAO {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public BigDecimal getInfectedSurvivorsPercentage() {
    return getSurvivorPercentage(true);
  }

  public BigDecimal getHealthySurvivorsPercentage() {
    return getSurvivorPercentage(false);
  }

  public Map<String, BigDecimal> getAverageSuppliesPerSurvivor() {
    String query = "select "
      + "s.name as name, "
      + "sum(sb.quantity) as total "
      + "from supply_bag sb "
      + "join supply s on s.id = sb.supply_id "
      + "group by s.id";

    String countQuery = "select count(*) from survivor";

    BigDecimal count = jdbcTemplate.queryForObject(countQuery, new MapSqlParameterSource(), BigDecimal.class);

    final Map<String, BigDecimal> result = new HashMap<>();

    SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, new MapSqlParameterSource());

    while(rowSet.next()) {
      BigDecimal total = BigDecimal.valueOf(rowSet.getDouble("total"));

      result.put(rowSet.getString("name"), total.divide(count, RoundingMode.HALF_UP));
    }

    return result;
  }

  public Long getLostPoints() {
    String query = "select "
      + "coalesce(sum(sb.quantity * s.points), 0) "
      + "from supply_bag sb "
      + "join supply s on s.id = sb.supply_id "
      + "join bag b on sb.bag_id = b.id "
      + "join survivor sv on sv.bag_id = b.id "
      + "where sv.infected = true";

    return jdbcTemplate.queryForObject(query, new MapSqlParameterSource(), Long.class);
  }

  private BigDecimal getSurvivorPercentage(boolean infected) {

    String query = "select "
      + "count(case when infected = :infected then 1 else null end) as partial, "
      + "count(*) as total "
      + "from survivor";

    MapSqlParameterSource params = new MapSqlParameterSource().addValue("infected", infected);

    return jdbcTemplate.queryForObject(query, params, (rs, rowNumber) -> {
      double partial = rs.getDouble("partial");
      double total = rs.getDouble("total");

      return new BigDecimal((partial/total) * 100).setScale(2, RoundingMode.HALF_UP);
    });
  }
}