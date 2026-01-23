package com.example.TransportHC.repository.cost;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.example.TransportHC.entity.Cost;

public interface CostRepositoryCustom {

    List<Cost> findCostsByTruck(UUID truckId, LocalDate from, LocalDate to);

    BigDecimal sumCostByTruck(UUID truckId, LocalDate from, LocalDate to);

    List<Object[]> sumCostAllTrucks(LocalDate from, LocalDate to);

    BigDecimal sumAllCosts(LocalDate from, LocalDate to);

    BigDecimal sumCostBySchedule(UUID scheduleId);

    BigDecimal sumCostByDriver(UUID driverId, LocalDate from, LocalDate to);
}
