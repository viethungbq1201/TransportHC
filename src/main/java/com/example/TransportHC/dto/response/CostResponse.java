package com.example.TransportHC.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import com.example.TransportHC.entity.CostType;
import com.example.TransportHC.enums.ApproveStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CostResponse {
    Long costId;
    UserResponse userCost;

    String description;
    BigDecimal price;
    String documentaryProof;
    LocalDate date;

    @Enumerated(EnumType.STRING)
    ApproveStatus approveStatus;

    CostType costType;

    ScheduleResponse schedule;

    UserResponse approvedBy;
}
