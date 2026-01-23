package com.example.TransportHC.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ReportFromToRequest {
    @NotNull(message = "INVALID_INPUT_DATA")
    LocalDate from;

    @NotNull(message = "INVALID_INPUT_DATA")
    LocalDate to;
}
