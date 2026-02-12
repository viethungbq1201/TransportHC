package com.example.TransportHC.dto.response;

import com.example.TransportHC.enums.TruckStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TruckResponse {
    Long id;
    String licensePlate;
    Integer capacity;
    TruckStatus status;
}
