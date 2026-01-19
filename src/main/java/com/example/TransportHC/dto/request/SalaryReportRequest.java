package com.example.TransportHC.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

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
