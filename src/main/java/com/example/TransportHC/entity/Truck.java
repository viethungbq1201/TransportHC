package com.example.TransportHC.entity;

import com.example.TransportHC.enums.TruckStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Truck {

    @Id
    @GeneratedValue
    UUID truckId;

    String licensePlate;
    Integer capacity;

    @Enumerated(EnumType.STRING)
    TruckStatus status;
}
