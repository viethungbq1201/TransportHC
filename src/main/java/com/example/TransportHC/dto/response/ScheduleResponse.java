package com.example.TransportHC.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import com.example.TransportHC.enums.ScheduleStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleResponse {
    UUID scheduleId;
    LocalDate startDate;
    LocalDate endDate;

    @Enumerated(EnumType.STRING)
    ScheduleStatus approveStatus;

    String documentaryProof;

    BigDecimal reward;

    UserResponse approveBy;
    UserResponse driver;
    TruckResponse truck;
    RouteResponse route;
    TransactionResponse transaction;
}
