package com.example.TransportHC.repository.cost;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TransportHC.entity.Cost;

public interface CostRepository extends JpaRepository<Cost, Long>, CostRepositoryCustom {
}
