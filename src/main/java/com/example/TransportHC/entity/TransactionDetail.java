package com.example.TransportHC.entity;

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
public class TransactionDetail {

    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne
    Transaction transaction;

    @ManyToOne
    Product product;

    Integer quantity;
}
