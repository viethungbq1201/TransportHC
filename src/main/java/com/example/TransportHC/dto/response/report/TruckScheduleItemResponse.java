package com.example.TransportHC.dto.response.report;

import java.math.BigDecimal;
import java.time.LocalDate;


public record TruckScheduleItemResponse(
        Long scheduleId,
        String routeName,
        String driverUsername,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal reward,
        BigDecimal totalCost
) {}
