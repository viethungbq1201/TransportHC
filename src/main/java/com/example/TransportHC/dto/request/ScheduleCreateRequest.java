package com.example.TransportHC.dto.request;

import com.example.TransportHC.entity.Route;
import com.example.TransportHC.entity.Transaction;
import com.example.TransportHC.entity.Truck;
import com.example.TransportHC.entity.User;
import com.example.TransportHC.enums.ApproveStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ScheduleCreateRequest {
    LocalDate startDate;
    LocalDate endDate;

    BigDecimal reward;

    UUID driverId;
    UUID truckId;
    UUID routeId;
    UUID transactionId;
}
