package com.example.TransportHC.dto.request;

import java.math.BigDecimal;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SalaryReportRequest {
    BigDecimal basicSalary;
    BigDecimal reward;
    BigDecimal cost;
    BigDecimal advanceMoney;
}
