package com.example.TransportHC.dto.request;

import com.example.TransportHC.entity.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

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
