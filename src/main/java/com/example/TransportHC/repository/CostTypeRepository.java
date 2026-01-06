package com.example.TransportHC.repository;

import com.example.TransportHC.entity.CostType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CostTypeRepository extends JpaRepository<CostType, UUID> {

}
