package com.example.TransportHC.entity;

import jakarta.persistence.*;

import com.example.TransportHC.enums.TruckStatus;

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
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long truckId;

    String licensePlate;
    Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 255, columnDefinition = "varchar(255)")
    TruckStatus status;
}
