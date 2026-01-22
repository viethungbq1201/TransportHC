package com.example.TransportHC.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CostCreateRequest {
    String description;
    BigDecimal price;
    String documentaryProof;

    UUID costTypeId;

    UUID scheduleId;
}
