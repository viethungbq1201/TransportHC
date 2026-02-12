package com.example.TransportHC.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
    Long inventoryId;

    ProductResponse product;
    Integer quantity;
    Integer inTransit;
    LocalDateTime upToDate;
}
