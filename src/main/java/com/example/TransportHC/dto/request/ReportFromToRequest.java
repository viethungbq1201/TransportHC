package com.example.TransportHC.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ReportFromToRequest {
    @NotBlank(message = "INVALID_INPUT_DATA")
    LocalDate from;

    @NotBlank(message = "INVALID_INPUT_DATA")
    LocalDate to;
}
