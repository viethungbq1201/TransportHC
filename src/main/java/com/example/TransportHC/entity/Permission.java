package com.example.TransportHC.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Permission {

    @Id
    @GeneratedValue
    UUID id;

    @Column(unique = true)
    String code;

    String name;
    String description;

    @ManyToMany(mappedBy = "permissions")
    @Builder.Default
    Set<Role> roles = new HashSet<>();
}
