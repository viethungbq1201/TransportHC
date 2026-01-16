package com.example.TransportHC.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class InventoryFilterRequest {
    String productName;
    String categoryName;
    Integer quantityMin;
    Integer quantityMax;
    LocalDateTime fromDate;
    LocalDateTime toDate;
    Double totalMin;
    Double totalMax;
}
