package com.example.TransportHC.dto.response;


import com.example.TransportHC.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
    UUID inventoryId;

    ProductResponse product;
    Integer quantity;
    LocalDateTime upToDate;
}
