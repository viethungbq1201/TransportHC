package com.example.TransportHC.repository;

import com.example.TransportHC.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CostRepository extends JpaRepository<Cost, UUID> {

}
