package com.example.TransportHC.dto.response.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TruckScheduleDetailResponse {

    UUID scheduleId;
    String route;
    String driverName;

    LocalDate startDate;
    LocalDate endDate;

    BigDecimal reward;
    BigDecimal totalCost;
}
