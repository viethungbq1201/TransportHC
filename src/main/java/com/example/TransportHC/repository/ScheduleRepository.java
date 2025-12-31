package com.example.TransportHC.repository;

import com.example.TransportHC.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<TransactionDetail, String> {

}
