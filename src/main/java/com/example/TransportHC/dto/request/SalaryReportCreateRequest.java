package com.example.TransportHC.dto.request;

import java.time.YearMonth;

import jakarta.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SalaryReportCreateRequest {
    @NotBlank(message = "INVALID_INPUT_DATA")
    YearMonth yearMonth;
}
