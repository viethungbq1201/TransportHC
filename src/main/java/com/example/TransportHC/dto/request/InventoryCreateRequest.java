package com.example.TransportHC.dto.request;

import java.time.LocalDateTime;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class InventoryCreateRequest {
    @NotNull(message = "NULL_DATA_EXCEPTION")
    Long productId;

    @NotNull(message = "NULL_DATA_EXCEPTION")
    @Positive(message = "POSITIVE_DATA")
    Integer quantity;

    LocalDateTime upToDate;
}
