package com.example.TransportHC.repository;

import com.example.TransportHC.entity.SalaryReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SalaryReportRepository extends JpaRepository<SalaryReport, UUID> {
}
