package com.example.TransportHC.dto.response.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TruckCostDetailResponse {
    UUID costId;
    LocalDate date;
    String costType;
    BigDecimal amount;
    UUID scheduleCode;

}
