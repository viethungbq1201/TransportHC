package com.example.TransportHC.repository.salaryreport;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface SalaryReportRepositoryCustom {

    BigDecimal sumReward(UUID userId, LocalDate startDate, LocalDate endDate);

    BigDecimal sumCost(UUID userId, LocalDate startDate, LocalDate endDate);
}
