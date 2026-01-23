package com.example.TransportHC.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;

import com.example.TransportHC.enums.ApproveStatus;

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
public class Cost {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID costId;

    @ManyToOne
    User userCost;

    String description;
    BigDecimal price;
    String documentaryProof;
    LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "approve_status", length = 255, columnDefinition = "varchar(255)")
    ApproveStatus approveStatus;

    @ManyToOne
    CostType costType;

    @ManyToOne
    Schedule schedule;

    @ManyToOne
    User approvedBy;
}
