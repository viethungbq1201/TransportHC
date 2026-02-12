package com.example.TransportHC.dto.response.report;

import java.util.List;


public record TruckScheduleGroupResponse(
        Long truckId,
        String licensePlate,
        List<TruckScheduleItemResponse> schedules
) {}
