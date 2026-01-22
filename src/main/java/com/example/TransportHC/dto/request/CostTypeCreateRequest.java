package com.example.TransportHC.dto.request;

import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CostTypeCreateRequest {
    @Size(min = 4, max = 255, message = "INVALID_LENGTH_DATA")
    String name;
}
