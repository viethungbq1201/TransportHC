package com.example.TransportHC.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.TransportHC.entity.Cost;

public interface CostRepository extends JpaRepository<Cost, UUID> {
    @Query(
            """
		SELECT c
		FROM Cost c
		JOIN c.schedule s
		WHERE s.truck.truckId = :truckId
		AND c.date BETWEEN :from AND :to
		AND c.approveStatus = 'APPROVED'
		AND s.approveStatus = 'DONE'
	""")
    List<Cost> findCostsByTruck(UUID truckId, LocalDate from, LocalDate to);

    @Query(
            """
		SELECT COALESCE(SUM(c.price), 0)
		FROM Cost c
		JOIN c.schedule s
		WHERE s.truck.truckId = :truckId
		AND c.date BETWEEN :from AND :to
		AND c.approveStatus = 'APPROVED'
		AND s.approveStatus = 'DONE'
	""")
    BigDecimal sumCostByTruck(UUID truckId, LocalDate from, LocalDate to);

    @Query(
            """
		SELECT s.truck.truckId, SUM(c.price)
		FROM Cost c
		JOIN c.schedule s
		WHERE c.date BETWEEN :from AND :to
		AND c.approveStatus = 'APPROVED'
		AND s.approveStatus = 'DONE'
		GROUP BY s.truck.truckId
	""")
    List<Object[]> sumCostAllTrucks(LocalDate from, LocalDate to);

    @Query(
            """
		SELECT COALESCE(SUM(c.price),0)
		FROM Cost c
		WHERE c.date BETWEEN :from AND :to
		AND c.approveStatus = 'APPROVED'
	""")
    BigDecimal sumAllCosts(LocalDate from, LocalDate to);

    @Query(
            """
		SELECT COALESCE(SUM(c.price), 0)
		FROM Cost c
		WHERE c.schedule.schedulesId = :scheduleId
		AND c.approveStatus = 'APPROVED'
		AND c.schedule.approveStatus = 'DONE'
	""")
    BigDecimal sumCostBySchedule(UUID scheduleId);

    @Query(
            """
		SELECT COALESCE(SUM(c.price), 0)
		FROM Cost c
		WHERE c.userCost.userId = :driverId
		AND c.date BETWEEN :from AND :to
		AND c.approveStatus = 'APPROVED'
	""")
    BigDecimal sumCostByDriver(UUID driverId, LocalDate from, LocalDate to);
}
