package com.example.TransportHC.repository;

import com.example.TransportHC.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<TransactionDetail, UUID> {

}
