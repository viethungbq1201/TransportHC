package com.example.TransportHC.controller;

import java.util.List;


import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.TransportHC.dto.request.TruckCreateRequest;
import com.example.TransportHC.dto.request.TruckUpdateStatusRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.dto.response.TruckResponse;
import com.example.TransportHC.service.TruckService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/truck")
@RestController
public class TruckController {

    TruckService truckService;

    @PostMapping("/createTruck")
    ApiResponse<TruckResponse> createTruck(@RequestBody @Valid TruckCreateRequest request) {
        return ApiResponse.<TruckResponse>builder()
                .result(truckService.createTruck(request))
                .build();
    }

    @GetMapping("/viewTruck")
    ApiResponse<List<TruckResponse>> viewTruck() {
        return ApiResponse.<List<TruckResponse>>builder()
                .result(truckService.viewTruck())
                .build();
    }

    @PutMapping("/updateTruck/{truckId}")
    ApiResponse<TruckResponse> updateTruck(
            @PathVariable("truckId") Long id, @RequestBody @Valid TruckCreateRequest request) {
        return ApiResponse.<TruckResponse>builder()
                .result(truckService.updateTruck(id, request))
                .build();
    }

    @PutMapping("/updateStatusTruck/{truckId}")
    ApiResponse<TruckResponse> updateStatusTruck(
            @PathVariable("truckId") Long id, @RequestBody @Valid TruckUpdateStatusRequest request) {
        return ApiResponse.<TruckResponse>builder()
                .result(truckService.updateStatusTruck(id, request))
                .build();
    }

    @DeleteMapping("/deleteTruck/{truckId}")
    ApiResponse<String> deleteTruck(@PathVariable("truckId") Long id) {
        truckService.deleteTruck(id);
        return ApiResponse.<String>builder().result("Truck have been deleted").build();
    }
}
