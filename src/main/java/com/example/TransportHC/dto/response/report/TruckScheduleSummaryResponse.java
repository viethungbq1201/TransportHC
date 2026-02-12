package com.example.TransportHC.dto.response.report;

import java.math.BigDecimal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TruckScheduleSummaryResponse {

    Long truckId;
    String licensePlate;

    Long totalTrips;
    BigDecimal totalReward;
    BigDecimal totalCost;
}
