package com.example.TransportHC.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID productId;

    String name;
    String category;
    BigDecimal price;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    Inventory inventory;
}
