package com.example.TransportHC.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouteResponse {
    UUID id;
    String name;
    String start_point;
    String end_point;
    BigDecimal distance;
}
