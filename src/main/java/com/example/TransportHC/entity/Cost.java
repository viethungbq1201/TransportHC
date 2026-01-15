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
public class Cost {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID costId;

    String description;
    BigDecimal price;
    String documentaryProof;
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
