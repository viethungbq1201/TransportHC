package com.example.TransportHC.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
    UUID inventoryId;

    ProductResponse product;
    Integer quantity;
    Integer inTransit;
    LocalDateTime upToDate;
}
