package com.example.TransportHC.dto.response.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TruckTripCountResponse {

    UUID truckId;
    String licensePlate;
    Long tripCount;
}

