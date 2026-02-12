package com.example.TransportHC.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import com.example.TransportHC.dto.response.PageResponse;
import com.example.TransportHC.entity.Schedule;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.TransportHC.dto.request.ReportFromToRequest;
import com.example.TransportHC.dto.response.report.*;
import com.example.TransportHC.entity.Truck;
import com.example.TransportHC.entity.User;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.TruckRepository;
import com.example.TransportHC.repository.cost.CostRepository;
import com.example.TransportHC.repository.schedule.ScheduleRepository;
import com.example.TransportHC.repository.user.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportService {

    CostRepository costRepository;
    ScheduleRepository scheduleRepository;
    TruckRepository truckRepository;
    UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public TruckCostReportResponse reportCostForTruck(Long truckId, ReportFromToRequest request) {
        Truck truck = truckRepository.findById(truckId).orElseThrow(() -> new AppException(ErrorCode.TRUCK_NOT_FOUND));

        BigDecimal totalCost = costRepository.sumCostByTruck(truckId, request.getFrom(), request.getTo());

        List<TruckCostDetailResponse> details =
                costRepository.findCostsByTruck(truckId, request.getFrom(), request.getTo()).stream()
                        .map(c -> TruckCostDetailResponse.builder()
                                .costId(c.getCostId())
                                .date(c.getDate())
                                .costType(c.getCostType().getName())
                                .amount(c.getPrice())
                                .scheduleCode(c.getSchedule().getSchedulesId())
                                .build())
                        .toList();

        return TruckCostReportResponse.builder()
                .truckId(truck.getTruckId())
                .licensePlate(truck.getLicensePlate())
                .fromDate(request.getFrom())
                .toDate(request.getTo())
                .totalCost(totalCost)
                .details(details)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<TruckCostSummaryResponse> reportCostAllTrucks(ReportFromToRequest request) {
        return costRepository.sumCostAllTrucks(request.getFrom(), request.getTo())
                .stream()
                .map(r -> new TruckCostSummaryResponse(
                        (Long) r[0],
                        (String) r[1],
                        (BigDecimal) r[2]
                ))
                .toList();

    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<TruckScheduleDetailResponse> reportRewardForTruck(Long truckId, ReportFromToRequest request) {
        List<Schedule> schedules = scheduleRepository.findSchedulesByTruckAndDate(truckId, request.getFrom(), request.getTo());

        Map<Long, BigDecimal> costMap =
                costRepository.sumCostBySchedule(
                        schedules.stream().map(Schedule::getSchedulesId).toList()
                );

        return schedules.stream()
                .map(s -> new TruckScheduleDetailResponse(
                        s.getSchedulesId(),
                        s.getRoute().getName(),
                        s.getDriver().getUsername(),
                        s.getStartDate(),
                        s.getEndDate(),
                        s.getReward(),
                        costMap.getOrDefault(
                                s.getSchedulesId(),
                                BigDecimal.ZERO
                        )
                ))
                .toList();

    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<TruckScheduleSummaryResponse> reportRewardAllTrucks(ReportFromToRequest request) {
        return scheduleRepository.summarySchedulesAllTrucks(request.getFrom(), request.getTo()).stream()
                .map(r -> new TruckScheduleSummaryResponse(
                        (Long) r[0],
                        (String) r[1],
                        (Long) r[2],
                        (BigDecimal) r[3],
                        (BigDecimal) r[4]
                ))
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public TruckScheduleGroupResponse reportSchedulesForOneTruck(
            Long truckId,
            ReportFromToRequest request) {

        List<ScheduleWithCostDto> rows =
                scheduleRepository.findSchedulesWithCost(
                        truckId,
                        request.getFrom(),
                        request.getTo()
                );

        if (rows.isEmpty()) {
            throw new AppException(ErrorCode.SCHEDULE_NOT_FOUND);
        }

        List<TruckScheduleItemResponse> schedules =
                rows.stream()
                        .map(r -> new TruckScheduleItemResponse(
                                r.scheduleId(),
                                r.routeName(),
                                r.driverUsername(),
                                r.startDate(),
                                r.endDate(),
                                r.reward(),
                                r.totalCost()
                        ))
                        .toList();

        return new TruckScheduleGroupResponse(
                rows.getFirst().truckId(),
                rows.getFirst().licensePlate(),
                schedules
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<TruckScheduleGroupResponse> reportSchedulesForAllTrucks(
            ReportFromToRequest request,
            Pageable pageable) {

        List<ScheduleWithCostDto> rows =
                scheduleRepository.findSchedulesWithCostForAllTrucks(
                        request.getFrom(),
                        request.getTo()
                );

        List<TruckScheduleGroupResponse> all =
                rows.stream()
                        .collect(Collectors.groupingBy(ScheduleWithCostDto::truckId))
                        .values()
                        .stream()
                        .map(list -> {
                            ScheduleWithCostDto first = list.getFirst();

                            List<TruckScheduleItemResponse> schedules =
                                    list.stream()
                                            .map(r -> new TruckScheduleItemResponse(
                                                    r.scheduleId(),
                                                    r.routeName(),
                                                    r.driverUsername(),
                                                    r.startDate(),
                                                    r.endDate(),
                                                    r.reward(),
                                                    r.totalCost()
                                            ))
                                            .toList();

                            return new TruckScheduleGroupResponse(
                                    first.truckId(),
                                    first.licensePlate(),
                                    schedules
                            );
                        })
                        .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), all.size());

        List<TruckScheduleGroupResponse> pageContent =
                start >= all.size() ? List.of() : all.subList(start, end);

        return PageResponse.<TruckScheduleGroupResponse>builder()
                .content(pageContent)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalElements(all.size())
                .totalPages((int) Math.ceil((double) all.size() / pageable.getPageSize()))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<TruckTripCountResponse> reportTripCountByTruck(ReportFromToRequest request) {
        return scheduleRepository.countTripsByTruck(request.getFrom(), request.getTo()).stream()
                .map(r -> new TruckTripCountResponse(
                        (Long) r[0],
                        truckRepository.getReferenceById((Long) r[0]).getLicensePlate(),
                        (Long) r[1]))
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public DriverCostReportResponse reportCostForDriver(Long driverId, ReportFromToRequest request) {
        User driver = userRepository.findById(driverId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        BigDecimal total = costRepository.sumCostByDriver(driverId, request.getFrom(), request.getTo());

        return DriverCostReportResponse.builder()
                .driverId(driverId)
                .driverName(driver.getFullName())
                .fromDate(request.getFrom())
                .toDate(request.getTo())
                .totalCost(total)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CostSummaryResponse reportSystemCost(ReportFromToRequest request) {
        return CostSummaryResponse.builder()
                .fromDate(request.getFrom())
                .toDate(request.getTo())
                .totalCost(costRepository.sumAllCosts(request.getFrom(), request.getTo()))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public TruckDailyReportResponse reportScheduleByTruck (Long truckId, ReportFromToRequest request) {

        List<TruckScheduleReportRow> rows =
                scheduleRepository.reportByTruckAndRow(truckId, request.getFrom(), request.getTo());

        Long totalExtraTrip =
                scheduleRepository.sumExtraTripCount(truckId, request.getFrom(), request.getTo());

        return new TruckDailyReportResponse(
                rows,
                totalExtraTrip == null ? 0 : totalExtraTrip
        );
    }

}
