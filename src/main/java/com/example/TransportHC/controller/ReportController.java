package com.example.TransportHC.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

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

    @PostMapping("/reportSchedulesForTruck/{reportId}")
    ApiResponse<List<TruckScheduleDetailResponse>> reportSchedulesForTruck(
            @PathVariable("reportId") UUID id, @RequestBody @Valid ReportFromToRequest request) {
        return ApiResponse.<List<TruckScheduleDetailResponse>>builder()
                .result(reportService.reportSchedulesForTruck(id, request))
                .build();
    }

    @PostMapping("/reportSchedulesAllTrucks")
    ApiResponse<List<TruckScheduleSummaryResponse>> reportSchedulesAllTrucks(
            @RequestBody @Valid ReportFromToRequest request) {
        return ApiResponse.<List<TruckScheduleSummaryResponse>>builder()
                .result(reportService.reportSchedulesAllTrucks(request))
                .build();
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
}
