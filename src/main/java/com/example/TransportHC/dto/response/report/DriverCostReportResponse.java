package com.example.TransportHC.dto.response.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverCostReportResponse {

    UUID driverId;
    String driverName;

    LocalDate fromDate;
    LocalDate toDate;

    BigDecimal totalCost;
}
