package com.example.TransportHC.dto.request;

import jakarta.validation.constraints.NotNull;

import com.example.TransportHC.enums.TruckStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TruckUpdateStatusRequest {
    @NotNull(message = "INVALID_INPUT_DATA")
    TruckStatus status;
}
