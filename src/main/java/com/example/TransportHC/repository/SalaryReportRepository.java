package com.example.TransportHC.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.TransportHC.entity.SalaryReport;

public interface SalaryReportRepository extends JpaRepository<SalaryReport, UUID> {

    boolean existsByUser_UserIdAndMonth(UUID userId, LocalDate month);

    @Query(
            value =
                    """
		SELECT COALESCE(SUM(reward),0)
		FROM schedule
		WHERE created_by = :userId
		AND approve_status = 'DONE'
		AND end_date BETWEEN :startDate AND :endDate
	""",
            nativeQuery = true)
    BigDecimal sumReward(UUID userId, LocalDate startDate, LocalDate endDate);

    @Query(
            value =
                    """
		SELECT COALESCE(SUM(price),0)
		FROM cost
		WHERE user_cost_user_id = :userId
		AND approve_status = 'APPROVED'
		AND date BETWEEN :startDate AND :endDate
	""",
            nativeQuery = true)
    BigDecimal sumCost(UUID userId, LocalDate startDate, LocalDate endDate);

    List<SalaryReport> findByMonth(LocalDate month);
}
