package com.example.TransportHC.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RouteCreateRequest {
    @NotBlank(message = "INVALID_INPUT_DATA")
    String name;

    @NotBlank(message = "INVALID_INPUT_DATA")
    String start_point;

    @NotBlank(message = "INVALID_INPUT_DATA")
    String end_point;

    @NotNull(message = "NULL_DATA_EXCEPTION")
    @Positive(message = "POSITIVE_DATA")
    BigDecimal distance;
}
