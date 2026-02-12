package com.example.TransportHC.dto.response.report;

import java.math.BigDecimal;
import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TruckCostDetailResponse {
    Long costId;
    LocalDate date;
    String costType;
    BigDecimal amount;
    Long scheduleCode;
}
