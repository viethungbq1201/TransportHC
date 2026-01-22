package com.example.TransportHC.dto.response.report;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TruckScheduleSummaryResponse {

    UUID truckId;
    String licensePlate;

    Long totalTrips;
    BigDecimal totalReward;
    BigDecimal totalCost;
}
