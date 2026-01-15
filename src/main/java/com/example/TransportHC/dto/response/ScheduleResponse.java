package com.example.TransportHC.dto.response;

import com.example.TransportHC.enums.ApproveStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

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
    ApproveStatus approveStatus;

    String documentaryProof;

    BigDecimal reward;

    UserResponse approveBy;
    UserResponse driver;
    TruckResponse truck;
    RouteResponse route;
    TransactionResponse transaction;
}
