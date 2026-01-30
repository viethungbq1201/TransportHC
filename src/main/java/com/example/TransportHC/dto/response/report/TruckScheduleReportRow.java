package com.example.TransportHC.dto.response.report;


import java.time.LocalDate;

public record TruckScheduleReportRow(
        String username,
        String fullName,
        String licensePlate,
        Integer capacity,
        LocalDate startDate,
        String startPoint,
        String endPoint,
        Long extraTripCount
) {}

