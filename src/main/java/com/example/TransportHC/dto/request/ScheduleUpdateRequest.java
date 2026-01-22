package com.example.TransportHC.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

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

    @NotNull(message = "NULL_DATA_EXCEPTION")
    @Positive(message = "POSITIVE_DATA")
    BigDecimal reward;

    @NotNull(message = "NULL_DATA_EXCEPTION")
    UUID driverId;

    @NotNull(message = "NULL_DATA_EXCEPTION")
    UUID truckId;

    @NotNull(message = "NULL_DATA_EXCEPTION")
    UUID routeId;

    @NotNull(message = "NULL_DATA_EXCEPTION")
    UUID transactionId;
}
