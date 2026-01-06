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
    private UUID reportId;

    @ManyToOne
    private User user;

    private String month;

    private BigDecimal basicSalary;
    private BigDecimal reward;
    private BigDecimal advanceMoney;
    private BigDecimal cost;
    private BigDecimal total;

    @ManyToOne
    private User createBy;

    private LocalDateTime createAt;
}
