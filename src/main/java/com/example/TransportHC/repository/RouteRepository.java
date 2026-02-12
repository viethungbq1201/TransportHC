package com.example.TransportHC.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TransportHC.entity.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {
    boolean existsRouteByName(String name);
}
