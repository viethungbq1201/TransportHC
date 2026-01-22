package com.example.TransportHC.dto.request;

import com.example.TransportHC.enums.TruckStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TruckUpdateStatusRequest {
    TruckStatus status;
}
