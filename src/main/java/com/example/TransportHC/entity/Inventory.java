package com.example.TransportHC.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Inventory {

    @Id
    UUID productId;

    @OneToOne
    @MapsId
    Product product;

    Integer quantity;
    LocalDateTime upToDate;
}
