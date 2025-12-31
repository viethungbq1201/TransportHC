package com.example.TransportHC.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Product {

    @Id
    @GeneratedValue
    UUID productId;

    String name;
    String category;
    BigDecimal price;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    Inventory inventory;
}
