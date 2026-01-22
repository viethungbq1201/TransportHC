package com.example.TransportHC.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CategoryCreateRequest {
    @NotBlank(message = "INVALID_INPUT_DATA")
    @Size(max = 50, message = "INVALID_LENGTH_DATA")
    String name;
}
