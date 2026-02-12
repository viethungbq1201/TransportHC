package com.example.TransportHC.controller;

import java.util.List;


import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.TransportHC.dto.request.RouteCreateRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.dto.response.RouteResponse;
import com.example.TransportHC.service.RouteService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/route")
@RestController
public class RouteController {

    RouteService routeService;

    @PostMapping("/createRoute")
    ApiResponse<RouteResponse> createRoute(@RequestBody @Valid RouteCreateRequest request) {
        return ApiResponse.<RouteResponse>builder()
                .result(routeService.createRoute(request))
                .build();
    }

    @GetMapping("viewRoute")
    ApiResponse<List<RouteResponse>> viewRoute() {
        return ApiResponse.<List<RouteResponse>>builder()
                .result(routeService.viewRoutes())
                .build();
    }

    @PutMapping("/updateRoute/{routeId}")
    ApiResponse<RouteResponse> updateRoute(
            @PathVariable("routeId") Long id, @RequestBody @Valid RouteCreateRequest request) {
        return ApiResponse.<RouteResponse>builder()
                .result(routeService.updateRoute(id, request))
                .build();
    }

    @DeleteMapping("/deleteRoute/{routeId}")
    ApiResponse<String> deleteRoute(@PathVariable("routeId") Long id) {
        routeService.deleteRoute(id);
        return ApiResponse.<String>builder().result("Route have been deleted").build();
    }
}
