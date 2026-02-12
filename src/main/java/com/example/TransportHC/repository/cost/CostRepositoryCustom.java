package com.example.TransportHC.repository.cost;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.TransportHC.entity.Cost;

public interface CostRepositoryCustom {

    List<Cost> findCostsByTruck(Long truckId, LocalDate from, LocalDate to);

    BigDecimal sumCostByTruck(Long truckId, LocalDate from, LocalDate to);

    List<Object[]> sumCostAllTrucks(LocalDate from, LocalDate to);

    BigDecimal sumAllCosts(LocalDate from, LocalDate to);

    Map<Long, BigDecimal> sumCostBySchedule(List<Long> scheduleIds);

    BigDecimal sumCostByDriver(Long driverId, LocalDate from, LocalDate to);
}
