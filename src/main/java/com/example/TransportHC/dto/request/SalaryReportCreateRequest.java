package com.example.TransportHC.dto.request;

import java.time.YearMonth;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SalaryReportCreateRequest {
    YearMonth yearMonth;
}
