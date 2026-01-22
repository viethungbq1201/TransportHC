package com.example.TransportHC.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ScheduleUpdateRequest {
    LocalDate startDate;
    LocalDate endDate;

    BigDecimal reward;

    UUID driverId;
    UUID truckId;
    UUID routeId;
    UUID transactionId;
}
