package com.example.TransportHC.dto.request;

import com.example.TransportHC.enums.ApproveStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

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
