package com.example.TransportHC.dto.response.report;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TruckTripCountResponse {

    Long truckId;
    String licensePlate;
    Long tripCount;
}
