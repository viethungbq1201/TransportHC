package com.example.TransportHC.dto.response;

import com.example.TransportHC.enums.TruckStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TruckResponse {
    UUID id;
    String licensePlate;
    Integer capacity;
    TruckStatus status;

}
