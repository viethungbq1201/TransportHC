package com.example.TransportHC.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CostCreateRequest {
    String description;
    BigDecimal price;

    UUID costTypeId;

    UUID scheduleId;

}
