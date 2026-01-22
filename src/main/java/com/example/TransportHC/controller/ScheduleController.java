package com.example.TransportHC.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.TransportHC.dto.request.ScheduleCreateRequest;
import com.example.TransportHC.dto.request.ScheduleEndRequest;
import com.example.TransportHC.dto.request.ScheduleUpdateRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.dto.response.ScheduleResponse;
import com.example.TransportHC.service.ScheduleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/schedule")
@RestController
public class ScheduleController {

    ScheduleService scheduleService;

    @PostMapping("/createSchedule")
    ApiResponse<ScheduleResponse> createSchedule(@RequestBody @Valid ScheduleCreateRequest request) {
        return ApiResponse.<ScheduleResponse>builder()
                .result(scheduleService.createSchedule(request))
                .build();
    }

    @GetMapping("viewSchedule")
    ApiResponse<List<ScheduleResponse>> viewSchedule() {
        return ApiResponse.<List<ScheduleResponse>>builder()
                .result(scheduleService.viewSchedule())
                .build();
    }

    @PutMapping("/updateSchedule/{ScheduleId}")
    ApiResponse<ScheduleResponse> updateSchedule(
            @PathVariable("ScheduleId") UUID id, @RequestBody @Valid ScheduleUpdateRequest request) {
        return ApiResponse.<ScheduleResponse>builder()
                .result(scheduleService.updateSchedule(id, request))
                .build();
    }

    @GetMapping("/approveSchedule/{ScheduleId}")
    ApiResponse<ScheduleResponse> approveSchedule(@PathVariable("ScheduleId") UUID id) {
        return ApiResponse.<ScheduleResponse>builder()
                .result(scheduleService.approveSchedule(id))
                .build();
    }

    @GetMapping("/rejectSchedule/{ScheduleId}")
    ApiResponse<ScheduleResponse> rejectSchedule(@PathVariable("ScheduleId") UUID id) {
        return ApiResponse.<ScheduleResponse>builder()
                .result(scheduleService.rejectSchedule(id))
                .build();
    }

    @PutMapping("/endSchedule/{ScheduleId}")
    ApiResponse<ScheduleResponse> endSchedule(
            @PathVariable("ScheduleId") UUID id, @RequestBody @Valid ScheduleEndRequest request) {
        return ApiResponse.<ScheduleResponse>builder()
                .result(scheduleService.endSchedule(id, request))
                .build();
    }

    @GetMapping("/cancelSchedule/{ScheduleId}")
    ApiResponse<ScheduleResponse> cancelSchedule(@PathVariable("ScheduleId") UUID id) {
        return ApiResponse.<ScheduleResponse>builder()
                .result(scheduleService.cancelSchedule(id))
                .build();
    }

    @DeleteMapping("/deleteSchedule/{ScheduleId}")
    ApiResponse<String> deleteSchedule(@PathVariable("ScheduleId") UUID id) {
        scheduleService.deleteSchedule(id);
        return ApiResponse.<String>builder()
                .result("Schedule have been deleted")
                .build();
    }
}
