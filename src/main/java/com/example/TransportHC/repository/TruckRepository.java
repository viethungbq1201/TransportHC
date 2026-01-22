package com.example.TransportHC.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TransportHC.entity.Truck;

public interface TruckRepository extends JpaRepository<Truck, UUID> {
    boolean existsTrucksByLicensePlate(String licensePlate);
}
