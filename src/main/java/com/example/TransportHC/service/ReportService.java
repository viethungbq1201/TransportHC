package com.example.TransportHC.service;

import com.example.TransportHC.dto.request.ReportFromToRequest;
import com.example.TransportHC.dto.response.report.*;
import com.example.TransportHC.entity.Truck;
import com.example.TransportHC.entity.User;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.CostRepository;
import com.example.TransportHC.repository.ScheduleRepository;
import com.example.TransportHC.repository.TruckRepository;
import com.example.TransportHC.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportService {

    CostRepository costRepository;
    ScheduleRepository scheduleRepository;
    TruckRepository truckRepository;
    UserRepository userRepository;

    public TruckCostReportResponse reportCostForTruck(UUID truckId, ReportFromToRequest request) {
        Truck truck = truckRepository.findById(truckId)
                .orElseThrow(() -> new AppException(ErrorCode.TRUCK_NOT_FOUND));

        BigDecimal totalCost = costRepository.sumCostByTruck(truckId, request.getFrom(), request.getTo());

        List<TruckCostDetailResponse> details = costRepository.findCostsByTruck(truckId, request.getFrom(), request.getTo())
                        .stream()
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

    public List<TruckCostSummaryResponse> reportCostAllTrucks(ReportFromToRequest request) {
        return costRepository.sumCostAllTrucks(request.getFrom(), request.getTo())
                .stream()
                .map(r -> {
                    UUID truckId = (UUID) r[0];
                    BigDecimal total = (BigDecimal) r[1];
                    Truck truck = truckRepository.getReferenceById(truckId);

                    return new TruckCostSummaryResponse(truckId, truck.getLicensePlate(), total);
                })
                .toList();
    }

    public List<TruckScheduleDetailResponse> reportSchedulesForTruck(UUID truckId, ReportFromToRequest request) {
        return scheduleRepository.findSchedulesByTruck(truckId, request.getFrom(), request.getTo())
                .stream()
                .map(s -> new TruckScheduleDetailResponse(
                        s.getSchedulesId(),
                        s.getRoute().getName(),
                        s.getDriver().getUsername(),
                        s.getStartDate(),
                        s.getEndDate(),
                        s.getReward(),
                        costRepository.sumCostBySchedule(s.getSchedulesId())
                ))
                .toList();
    }

    public List<TruckScheduleSummaryResponse> reportSchedulesAllTrucks(ReportFromToRequest request) {
        return scheduleRepository.summarySchedulesAllTrucks(request.getFrom(), request.getTo())
                .stream()
                .map(r -> new TruckScheduleSummaryResponse(
                        (UUID) r[0],
                        truckRepository.getReferenceById((UUID) r[0]).getLicensePlate(),
                        (Long) r[1],
                        (BigDecimal) r[2],
                        (BigDecimal) r[3]
                ))
                .toList();
    }


    public List<TruckTripCountResponse> reportTripCountByTruck(ReportFromToRequest request) {
        return scheduleRepository.countTripsByTruck(request.getFrom(), request.getTo())
                .stream()
                .map(r -> new TruckTripCountResponse(
                        (UUID) r[0],
                        truckRepository.getReferenceById((UUID) r[0]).getLicensePlate(),
                        (Long) r[1]
                ))
                .toList();
    }


    public DriverCostReportResponse reportCostForDriver(UUID driverId, ReportFromToRequest request) {
        User driver = userRepository.findById(driverId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        BigDecimal total = costRepository.sumCostByDriver(driverId, request.getFrom(), request.getTo());

        return DriverCostReportResponse.builder()
                .driverId(driverId)
                .driverName(driver.getFullName())
                .fromDate(request.getFrom())
                .toDate(request.getTo())
                .totalCost(total)
                .build();
    }

    public CostSummaryResponse reportSystemCost(ReportFromToRequest request) {
        return CostSummaryResponse.builder()
                .fromDate(request.getFrom())
                .toDate(request.getTo())
                .totalCost(costRepository.sumAllCosts(request.getFrom(), request.getTo()))
                .build();
    }

}
