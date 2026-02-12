package com.example.TransportHC.controller;

import java.util.List;


import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.TransportHC.dto.request.SalaryReportCreateRequest;
import com.example.TransportHC.dto.request.SalaryReportRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.dto.response.SalaryReportResponse;
import com.example.TransportHC.dto.response.SalaryReportSummaryResponse;
import com.example.TransportHC.service.SalaryReportService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/salaryReport")
@RestController
public class SalaryReportController {

    SalaryReportService salaryReportService;

    @PostMapping("/create1/{salaryReportId}")
    ApiResponse<SalaryReportResponse> create1SalaryReport(
            @PathVariable("salaryReportId") Long id, @RequestBody @Valid SalaryReportRequest request) {
        return ApiResponse.<SalaryReportResponse>builder()
                .result(salaryReportService.create1SalaryReport(id, request))
                .build();
    }

    @GetMapping("/createAllSalaryReport")
    ApiResponse<List<SalaryReportResponse>> createAllSalaryReport() {
        return ApiResponse.<List<SalaryReportResponse>>builder()
                .result(salaryReportService.createAllSalaryReport())
                .build();
    }

    @PostMapping("/viewSalaryReport")
    ApiResponse<List<SalaryReportSummaryResponse>> viewSalaryReport(
            @RequestBody @Valid SalaryReportCreateRequest request) {
        return ApiResponse.<List<SalaryReportSummaryResponse>>builder()
                .result(salaryReportService.viewSalaryReportByMonth(request.getYearMonth()))
                .build();
    }

    @GetMapping("/viewSalaryReportDetail/{salaryReportId}")
    ApiResponse<SalaryReportResponse> viewSalaryReportDetail(@PathVariable("salaryReportId") Long id) {
        return ApiResponse.<SalaryReportResponse>builder()
                .result(salaryReportService.viewSalaryReportDetail(id))
                .build();
    }

    @PutMapping("/updateSalaryReport/{salaryReportId}")
    ApiResponse<SalaryReportResponse> updateSalaryReport(
            @PathVariable("salaryReportId") Long id, @RequestBody @Valid SalaryReportRequest request) {
        return ApiResponse.<SalaryReportResponse>builder()
                .result(salaryReportService.updateSalaryReport(id, request))
                .build();
    }

    @DeleteMapping("/deleteSalaryReport/{salaryReportId}")
    ApiResponse<String> deleteSalaryReport(@PathVariable("salaryReportId") Long id) {
        salaryReportService.deleteSalaryReport(id);
        return ApiResponse.<String>builder().result("Salary Report deleted").build();
    }

    @GetMapping("/doneSalaryReport/{salaryReportId}")
    ApiResponse<SalaryReportResponse> doneSalaryReport(@PathVariable("salaryReportId") Long id) {
        return ApiResponse.<SalaryReportResponse>builder()
                .result(salaryReportService.checkSalaryReport(id))
                .build();
    }
}
