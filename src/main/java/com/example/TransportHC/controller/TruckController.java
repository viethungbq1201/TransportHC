package com.example.TransportHC.controller;

import com.example.TransportHC.dto.request.TruckCreateRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.dto.response.TruckResponse;
import com.example.TransportHC.service.TruckService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/truck")
@RestController
public class TruckController {

    TruckService truckService;

    @PostMapping("/createTruck")
    ApiResponse<TruckResponse> createTruck (@RequestBody TruckCreateRequest request) {
        return ApiResponse.<TruckResponse>builder()
                .result(truckService.createTruck(request))
                .build();
    }

    @GetMapping("/viewTruck")
    ApiResponse<List<TruckResponse>> viewTruck () {
        return ApiResponse.<List<TruckResponse>>builder()
                .result(truckService.viewTruck())
                .build();
    }

    @PutMapping("/updateTruck/{truckId}")
    ApiResponse<TruckResponse> updateTruck (@PathVariable("truckId") UUID id, @RequestBody TruckCreateRequest request) {
        return ApiResponse.<TruckResponse>builder()
                .result(truckService.updateTruck(id,request))
                .build();
    }

    @DeleteMapping("/deleteTruck/{truckId}")
    String deleteTruck (@PathVariable("truckId") UUID id) {
        truckService.deleteTruck(id);
        return "Truck has been deleted";
    }
}
