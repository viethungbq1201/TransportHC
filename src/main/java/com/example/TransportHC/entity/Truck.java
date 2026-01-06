package com.example.TransportHC.entity;

import com.example.TransportHC.enums.TruckStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID truckId;

    String licensePlate;
    Integer capacity;

    @Enumerated(EnumType.STRING)
    TruckStatus status;
}
