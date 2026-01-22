package com.example.TransportHC.dto.response.report;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TruckCostSummaryResponse {

    UUID truckId;
    String licensePlate;
    BigDecimal totalCost;
}
