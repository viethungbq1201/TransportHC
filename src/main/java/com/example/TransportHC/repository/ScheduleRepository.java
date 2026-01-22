package com.example.TransportHC.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.TransportHC.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {

    @Query(
            """
		SELECT s
		FROM Schedule s
		WHERE s.truck.truckId = :truckId
		AND s.endDate BETWEEN :from AND :to
	""")
    List<Schedule> findSchedulesByTruck(UUID truckId, LocalDate from, LocalDate to);

    @Query(
            """
		SELECT s.truck.truckId,
			COUNT(s.schedulesId),
			SUM(s.reward),
			SUM(c.price)
		FROM Schedule s
		LEFT JOIN Cost c ON c.schedule.schedulesId = s.schedulesId
		WHERE s.endDate BETWEEN :from AND :to
		GROUP BY s.truck.truckId
	""")
    List<Object[]> summarySchedulesAllTrucks(LocalDate from, LocalDate to);

    @Query(
            """
		SELECT s.truck.truckId, COUNT(s.schedulesId)
		FROM Schedule s
		WHERE s.endDate BETWEEN :from AND :to
		GROUP BY s.truck.truckId
	""")
    List<Object[]> countTripsByTruck(LocalDate from, LocalDate to);
}
