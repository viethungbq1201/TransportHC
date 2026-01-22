package com.example.TransportHC.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;

import com.example.TransportHC.enums.UserStatus;

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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    UUID userId;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false)
    String password;

    String fullName;
    String address;
    String phoneNumber;

    @Enumerated(EnumType.STRING)
    UserStatus status;

    BigDecimal basicSalary;
    BigDecimal advanceMoney;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Builder.Default
    Set<Role> roles = new HashSet<>();

    Boolean isDriver;
}
