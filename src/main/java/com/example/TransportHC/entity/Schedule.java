package com.example.TransportHC.entity;

import com.example.TransportHC.enums.ApproveStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

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
        ApproveStatus approveStatus;

    String documentaryProof;
    BigDecimal reward;

    @ManyToOne
    @JoinColumn(name = "created_by")
    User driver;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    User approvedBy;

    @ManyToOne
    Truck truck;

    @ManyToOne
    Route route;

    @ManyToOne
    Transaction transaction;

}
