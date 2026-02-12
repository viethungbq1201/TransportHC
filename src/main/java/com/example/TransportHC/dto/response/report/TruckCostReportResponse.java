package com.example.TransportHC.dto.response.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TruckCostReportResponse {
    Long truckId;
    String licensePlate;

    LocalDate fromDate;
    LocalDate toDate;

    BigDecimal totalCost;

    List<TruckCostDetailResponse> details;
}
