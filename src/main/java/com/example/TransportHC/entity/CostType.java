package com.example.TransportHC.entity;

import jakarta.persistence.*;

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
public class CostType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long costTypeId;

    String name;
}
