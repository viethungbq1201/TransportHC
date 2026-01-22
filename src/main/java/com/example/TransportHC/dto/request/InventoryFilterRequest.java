package com.example.TransportHC.dto.request;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
