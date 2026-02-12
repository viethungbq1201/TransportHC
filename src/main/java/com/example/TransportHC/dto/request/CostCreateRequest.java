package com.example.TransportHC.dto.request;

import java.math.BigDecimal;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CostCreateRequest {

    @NotBlank(message = "INVALID_INPUT_DATA")
    @Size(min = 4, max = 255)
    String description;

    @NotNull(message = "NULL_DATA_EXCEPTION")
    @Positive(message = "POSITIVE_DATA")
    BigDecimal price;

    String documentaryProof;

    @NotNull(message = "NULL_DATA_EXCEPTION")
    Long costTypeId;

    @NotNull(message = "NULL_DATA_EXCEPTION")
    Long scheduleId;
}
