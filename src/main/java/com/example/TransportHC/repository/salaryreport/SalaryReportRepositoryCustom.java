package com.example.TransportHC.repository.salaryreport;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface SalaryReportRepositoryCustom {

    BigDecimal sumReward(Long userId, LocalDate startDate, LocalDate endDate);

    BigDecimal sumCost(Long userId, LocalDate startDate, LocalDate endDate);
}
