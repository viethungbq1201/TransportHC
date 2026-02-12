package com.example.TransportHC.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TransportHC.entity.Truck;

public interface TruckRepository extends JpaRepository<Truck, Long> {
    boolean existsTrucksByLicensePlate(String licensePlate);
}
