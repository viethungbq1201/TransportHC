package com.example.TransportHC.repository.salaryreport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TransportHC.entity.SalaryReport;

public interface SalaryReportRepository extends JpaRepository<SalaryReport, Long>, SalaryReportRepositoryCustom {

    boolean existsByUser_UserIdAndMonth(Long userId, java.time.LocalDate month);

    List<SalaryReport> findByMonth(java.time.LocalDate month);
}
