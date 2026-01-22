package com.example.TransportHC.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;

import com.example.TransportHC.enums.ScheduleStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID schedulesId;

    LocalDate startDate;
    LocalDate endDate;

    @Enumerated(EnumType.STRING)
    ScheduleStatus approveStatus;

    String documentaryProof;
    BigDecimal reward;

    @ManyToOne
    @JoinColumn(name = "created_by")
    User driver;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    User approvedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    Truck truck;

    @ManyToOne(fetch = FetchType.LAZY)
    Route route;

    @OneToOne
    @JoinColumn(unique = true)
    private Transaction transaction;
}
