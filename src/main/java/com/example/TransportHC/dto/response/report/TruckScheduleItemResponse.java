package com.example.TransportHC.dto.response.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TruckScheduleItemResponse(
        UUID scheduleId,
        String routeName,
        String driverUsername,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal reward,
        BigDecimal totalCost
) {}
