package com.example.TransportHC.service;

import java.util.List;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.TransportHC.dto.request.RouteCreateRequest;
import com.example.TransportHC.dto.response.RouteResponse;
import com.example.TransportHC.entity.Route;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.RouteRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RouteService {
    RouteRepository routeRepository;

    @PreAuthorize("hasAuthority('CREATE_ROUTE')")
    public RouteResponse createRoute(RouteCreateRequest request) {

        if (routeRepository.existsRouteByName(request.getName())) {
            throw new AppException(ErrorCode.ROUTE_EXISTED);
        }
        Route route = Route.builder()
                .name(request.getName())
                .start_point(request.getStart_point())
                .end_point(request.getEnd_point())
                .distance(request.getDistance())
                .build();
        routeRepository.save(route);
        return entityToResponse(route);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('VIEW_ROUTE')")
    public List<RouteResponse> viewRoutes() {
        return routeRepository.findAll().stream().map(this::entityToResponse).toList();
    }

    @PreAuthorize("hasAuthority('UPDATE_ROUTE')")
    public RouteResponse updateRoute(Long id, RouteCreateRequest request) {
        Route route = routeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROUTE_NOT_FOUND));

        route.setName(request.getName());
        route.setStart_point(request.getStart_point());
        route.setEnd_point(request.getEnd_point());
        route.setDistance(request.getDistance());
        routeRepository.save(route);
        return entityToResponse(route);
    }

    @PreAuthorize("hasAuthority('DELETE_ROUTE')")
    public void deleteRoute(Long id) {
        Route route = routeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROUTE_NOT_FOUND));
        routeRepository.delete(route);
    }

    private RouteResponse entityToResponse(Route route) {
        return RouteResponse.builder()
                .id(route.getRouteId())
                .name(route.getName())
                .start_point(route.getStart_point())
                .end_point(route.getEnd_point())
                .distance(route.getDistance())
                .build();
    }
}
