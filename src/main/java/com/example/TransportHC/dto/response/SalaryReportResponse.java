package com.example.TransportHC.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.TransportHC.enums.SalaryReportStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SalaryReportResponse {
    UUID salaryReportId;
    UserResponse user;
    BigDecimal basic_salary;
    BigDecimal advance_salary;
    BigDecimal cost;
    BigDecimal reward;
    BigDecimal total_salary;
    LocalDateTime createdAt;
    LocalDate month;
    UserResponse createBy;
    SalaryReportStatus status;
}
