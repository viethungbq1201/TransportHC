package com.example.TransportHC.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TransportHC.entity.Route;

public interface RouteRepository extends JpaRepository<Route, UUID> {
    boolean existsRouteByName(String name);
}
