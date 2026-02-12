package com.example.TransportHC.dto.response.report;

import java.math.BigDecimal;
import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TruckScheduleDetailResponse {

    Long scheduleId;
    String route;
    String driverName;

    LocalDate startDate;
    LocalDate endDate;

    BigDecimal reward;
    BigDecimal totalCost;
}
