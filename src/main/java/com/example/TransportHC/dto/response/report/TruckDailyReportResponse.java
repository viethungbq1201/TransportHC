package com.example.TransportHC.dto.response.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class TruckDailyReportResponse {
    List<TruckScheduleReportRow> data;
    long totalExtraTripCount;
}

