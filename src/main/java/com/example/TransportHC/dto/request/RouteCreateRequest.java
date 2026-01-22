package com.example.TransportHC.dto.request;

import java.math.BigDecimal;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RouteCreateRequest {
    String name;
    String start_point;
    String end_point;
    BigDecimal distance;
}
