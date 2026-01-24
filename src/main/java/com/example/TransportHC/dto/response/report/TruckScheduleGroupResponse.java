package com.example.TransportHC.dto.response.report;

import java.util.List;
import java.util.UUID;

public record TruckScheduleGroupResponse(
        UUID truckId,
        String licensePlate,
        List<TruckScheduleItemResponse> schedules
) {}
