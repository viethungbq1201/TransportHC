package com.example.TransportHC.repository.schedule;

import java.time.LocalDate;
import java.util.List;

import com.example.TransportHC.dto.response.report.ScheduleWithCostDto;
import com.example.TransportHC.dto.response.report.TruckScheduleReportRow;
import com.example.TransportHC.entity.Schedule;

public interface ScheduleRepositoryCustom {

        List<Schedule> findSchedulesByTruck(Long truckId, LocalDate from, LocalDate to);

        List<Object[]> summarySchedulesAllTrucks(LocalDate from, LocalDate to);

        List<Object[]> countTripsByTruck(LocalDate from, LocalDate to);

        List<Schedule> findSchedulesByTruckAndDate(
                        Long truckId,
                        LocalDate from,
                        LocalDate to);

        List<Schedule> findSchedulesByDate(
                        LocalDate from,
                        LocalDate to);

        List<ScheduleWithCostDto> findSchedulesWithCost(
                        Long truckId,
                        LocalDate from,
                        LocalDate to);

        List<ScheduleWithCostDto> findSchedulesWithCostForAllTrucks(
                        LocalDate from,
                        LocalDate to);

        List<TruckScheduleReportRow> reportByTruckAndRow(
                        Long truckId,
                        LocalDate from,
                        LocalDate to);

        Long sumExtraTripCount(
                        Long truckId,
                        LocalDate from,
                        LocalDate to);
}
