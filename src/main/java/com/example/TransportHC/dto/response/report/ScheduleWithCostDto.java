package com.example.TransportHC.dto.response.report;

import java.math.BigDecimal;
import java.time.LocalDate;


public record ScheduleWithCostDto(
        Long scheduleId,
        Long truckId,
        String licensePlate,
        String routeName,
        String driverUsername,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal reward,
        BigDecimal totalCost
) {}

