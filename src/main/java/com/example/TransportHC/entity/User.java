package com.example.TransportHC.entity;

import com.example.TransportHC.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User {

    @Id
    @GeneratedValue
    UUID userId;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false)
    String password;

    String fullname;
    String address;
    String phoneNumber;

    @Enumerated(EnumType.STRING)
    UserStatus status;

    BigDecimal basicSalary;
    BigDecimal advanceMoney;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    Set<Role> roles = new HashSet<>();
}
