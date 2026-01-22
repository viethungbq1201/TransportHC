package com.example.TransportHC.dto.response.report;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TruckTripCountResponse {

    UUID truckId;
    String licensePlate;
    Long tripCount;
}
