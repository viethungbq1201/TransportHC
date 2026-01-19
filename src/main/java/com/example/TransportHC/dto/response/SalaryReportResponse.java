package com.example.TransportHC.dto.response;

import com.example.TransportHC.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SalaryReportResponse {
    UUID salaryReportId;
    UUID userId;
    BigDecimal basic_salary;
    BigDecimal cost;
    BigDecimal reward;
    BigDecimal total_salary;
    LocalDateTime createdAt;
    Month month;
    User CreateBy;

}
