package com.example.TransportHC.dto.request;

import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TransactionDetailCreateRequest {
    UUID transactionId;
    UUID productId;

    Integer quantityChange;
    Integer quantityBefore;
    Integer quantityAfter;
}
