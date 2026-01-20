package com.example.TransportHC.dto.response.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TruckCostReportResponse {
    UUID truckId;
    String licensePlate;

    LocalDate fromDate;
    LocalDate toDate;

    BigDecimal totalCost;

    List<TruckCostDetailResponse> details;

}
