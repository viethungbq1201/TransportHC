package com.example.TransportHC.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.UUID;

import jakarta.persistence.*;

import com.example.TransportHC.enums.SalaryReportStatus;

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
public class SalaryReport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID reportId;

    @ManyToOne
    User user;

    YearMonth month;

    BigDecimal basicSalary;
    BigDecimal reward;
    BigDecimal advanceMoney;
    BigDecimal cost;
    BigDecimal total;

    @ManyToOne
    User createBy;

    LocalDateTime createAt;
    SalaryReportStatus status;
}
