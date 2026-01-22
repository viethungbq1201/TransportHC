package com.example.TransportHC.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import com.example.TransportHC.enums.TruckStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TruckCreateRequest {
    @NotBlank(message = "INVALID_INPUT_DATA")
    String licensePlate;

    @NotNull(message = "NULL_DATA_EXCEPTION")
    @Positive(message = "POSITIVE_DATA")
    Integer capacity;

    @NotBlank(message = "INVALID_INPUT_DATA")
    TruckStatus status;
}
