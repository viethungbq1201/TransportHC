package com.example.TransportHC.repository;

import com.example.TransportHC.entity.CostType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostRepository extends JpaRepository<CostType, String> {

}
