package com.example.TransportHC.entity;

import com.example.TransportHC.enums.ApproveStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Schedule {

    @Id
    @GeneratedValue
    UUID schedulesId;

    LocalDate startDate;
    LocalDate endDate;

    @Enumerated(EnumType.STRING)
    ApproveStatus approveStatus;

    String documentaryProof;
    BigDecimal reward;

    @ManyToOne
    @JoinColumn(name = "created_by")
    User driver;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    User approver;

    @ManyToOne
    Truck truck;

    @ManyToOne
    Route route;
}
