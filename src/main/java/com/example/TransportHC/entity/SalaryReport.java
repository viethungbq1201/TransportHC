package com.example.TransportHC.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SalaryReport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID reportId;

    @ManyToOne
    User user;

    String month;

    BigDecimal basicSalary;
    BigDecimal reward;
    BigDecimal advanceMoney;
    BigDecimal cost;
    BigDecimal total;

    @ManyToOne
    User createBy;

    LocalDateTime createAt;
}
