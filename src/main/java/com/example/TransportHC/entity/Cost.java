package com.example.TransportHC.entity;

import com.example.TransportHC.enums.ApproveStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

public class Cost {

    @Id
    @GeneratedValue
    UUID costId;

    String description;
    BigDecimal price;
    LocalDate date;

    @Enumerated(EnumType.STRING)
    ApproveStatus approveStatus;

    @ManyToOne
    CostType costType;

    @ManyToOne
    Schedule schedule;

    @ManyToOne
    User approvedBy;
}
