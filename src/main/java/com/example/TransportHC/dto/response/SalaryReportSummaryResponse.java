package com.example.TransportHC.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.example.TransportHC.enums.SalaryReportStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SalaryReportSummaryResponse {
    UUID reportId;
    UserResponse user;
    BigDecimal baseSalary;
    BigDecimal advanceSalary;
    BigDecimal rewardSalary;
    BigDecimal costSalary;
    BigDecimal total;
    LocalDate month;
    SalaryReportStatus status;
}
