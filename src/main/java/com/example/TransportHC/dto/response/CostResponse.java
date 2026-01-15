package com.example.TransportHC.dto.response;

import com.example.TransportHC.entity.CostType;
import com.example.TransportHC.entity.Schedule;
import com.example.TransportHC.entity.User;
import com.example.TransportHC.enums.ApproveStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CostResponse {
    UUID costId;
    String description;
    BigDecimal price;
    LocalDate date;

    @Enumerated(EnumType.STRING)
    ApproveStatus approveStatus;

    CostType costType;

    ScheduleResponse schedule;

    UserResponse approvedBy;
}
