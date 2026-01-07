package com.example.TransportHC.controller;

import com.example.TransportHC.dto.request.RouteCreateRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.dto.response.RouteResponse;
import com.example.TransportHC.entity.Route;
import com.example.TransportHC.service.RouteService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/route")
@RestController
public class RouteController {

    RouteService routeService;

    @PostMapping("/createRoute")
    ApiResponse<RouteResponse> createRoute (@RequestBody RouteCreateRequest request) {
        return ApiResponse.<RouteResponse>builder()
                .result(routeService.createRoute(request))
                .build();
    }

    @GetMapping("viewRoute")
    ApiResponse<List<RouteResponse>> viewRoute () {
        return ApiResponse.<List<RouteResponse>>builder()
                .result(routeService.viewRoutes())
                .build();
    }

    @PutMapping("/updateRoute/{routeId}")
    ApiResponse<RouteResponse> updateRoute (@PathVariable("routeId") UUID id, @RequestBody RouteCreateRequest request) {
        return ApiResponse.<RouteResponse>builder()
                .result(routeService.updateRoute(id, request))
                .build();
    }

    @DeleteMapping("/deleteRoute/{routeId}")
    String deleteRoute (@PathVariable("routeId") UUID id) {
        routeService.deleteRoute(id);
        return "Route has been deleted";
    }

}
