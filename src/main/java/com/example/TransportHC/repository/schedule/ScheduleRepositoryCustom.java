package com.example.TransportHC.repository.schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.example.TransportHC.dto.response.report.ScheduleWithCostDto;
import com.example.TransportHC.entity.Schedule;

public interface ScheduleRepositoryCustom {

    List<Schedule> findSchedulesByTruck(UUID truckId, LocalDate from, LocalDate to);

    List<Object[]> summarySchedulesAllTrucks(LocalDate from, LocalDate to);

    List<Object[]> countTripsByTruck(LocalDate from, LocalDate to);

    List<Schedule> findSchedulesByTruckAndDate(
            UUID truckId,
            LocalDate from,
            LocalDate to
    );

    List<Schedule> findSchedulesByDate(
            LocalDate from,
            LocalDate to
    );
    List<ScheduleWithCostDto> findSchedulesWithCost(
            UUID truckId,
            LocalDate from,
            LocalDate to
    );

    List<ScheduleWithCostDto> findSchedulesWithCostForAllTrucks(
            LocalDate from,
            LocalDate to
    );
}
