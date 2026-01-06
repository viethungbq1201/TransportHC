package com.example.TransportHC.repository;

import com.example.TransportHC.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TruckRepository extends JpaRepository<Truck, UUID> {

}
