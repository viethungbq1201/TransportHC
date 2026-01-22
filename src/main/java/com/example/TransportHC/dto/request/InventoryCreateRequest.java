package com.example.TransportHC.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class InventoryCreateRequest {
    UUID productId;
    Integer quantity;
    LocalDateTime upToDate;
}
