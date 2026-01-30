package com.example.TransportHC.controller;

import java.util.List;
import java.util.UUID;

import com.example.TransportHC.dto.response.PageResponse;
import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.example.TransportHC.dto.request.ReportFromToRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.dto.response.report.*;
import com.example.TransportHC.service.ReportService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/report")
@RestController
public class ReportController {

    ReportService reportService;

    @PostMapping("/reportCostForTruck/{reportId}")
    ApiResponse<TruckCostReportResponse> reportCostForTruck(
            @PathVariable("reportId") UUID id, @RequestBody @Valid ReportFromToRequest request) {
        return ApiResponse.<TruckCostReportResponse>builder()
                .result(reportService.reportCostForTruck(id, request))
                .build();
    }

    @PostMapping("/reportCostAllTrucks")
    ApiResponse<List<TruckCostSummaryResponse>> reportCostAllTrucks(@RequestBody @Valid ReportFromToRequest request) {
        return ApiResponse.<List<TruckCostSummaryResponse>>builder()
                .result(reportService.reportCostAllTrucks(request))
                .build();
    }

    @PostMapping("/reportRewardForTruck/{reportId}")
    ApiResponse<List<TruckScheduleDetailResponse>> reportRewardForTruck(
            @PathVariable("reportId") UUID id, @RequestBody @Valid ReportFromToRequest request) {
        return ApiResponse.<List<TruckScheduleDetailResponse>>builder()
                .result(reportService.reportRewardForTruck(id, request))
                .build();
    }

    @PostMapping("/reportRewardAllTrucks")
    ApiResponse<List<TruckScheduleSummaryResponse>> reportRewardAllTrucks(
            @RequestBody @Valid ReportFromToRequest request) {
        return ApiResponse.<List<TruckScheduleSummaryResponse>>builder()
                .result(reportService.reportRewardAllTrucks(request))
                .build();
    }

    @PostMapping("/reportSchedulesForOneTruck/{truckId}")
    public TruckScheduleGroupResponse reportSchedulesForOneTruck(
            @PathVariable UUID truckId,
            @RequestBody ReportFromToRequest request) {

        return reportService.reportSchedulesForOneTruck(truckId, request);
    }

    @PostMapping("/reportSchedulesForAllTrucks")
    public PageResponse<TruckScheduleGroupResponse> reportSchedulesForAllTrucks(
            @RequestBody ReportFromToRequest request,
            Pageable pageable) {

        return reportService.reportSchedulesForAllTrucks(request, pageable);
    }

    @PostMapping("/reportTripCountByTruck")
    ApiResponse<List<TruckTripCountResponse>> reportTripCountByTruck(@RequestBody @Valid ReportFromToRequest request) {
        return ApiResponse.<List<TruckTripCountResponse>>builder()
                .result(reportService.reportTripCountByTruck(request))
                .build();
    }

    @PostMapping("/reportCostForDriver/{reportId}")
    ApiResponse<DriverCostReportResponse> reportCostForDriver(
            @PathVariable("reportId") UUID id, @RequestBody @Valid ReportFromToRequest request) {
        return ApiResponse.<DriverCostReportResponse>builder()
                .result(reportService.reportCostForDriver(id, request))
                .build();
    }

    @PostMapping("/reportSystemCost")
    ApiResponse<CostSummaryResponse> reportSystemCost(@RequestBody @Valid ReportFromToRequest request) {
        return ApiResponse.<CostSummaryResponse>builder()
                .result(reportService.reportSystemCost(request))
                .build();
    }

    @PostMapping("/reportScheduleAllTruckRow/{userId}")
    ApiResponse<TruckDailyReportResponse> reportScheduleAllTruckRow(@PathVariable("userId") UUID id, @RequestBody @Valid ReportFromToRequest request) {
        return ApiResponse.<TruckDailyReportResponse>builder()
                .result(reportService.reportScheduleByTruck(id, request))
                .build();
    }
}
