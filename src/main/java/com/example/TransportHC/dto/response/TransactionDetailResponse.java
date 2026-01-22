package com.example.TransportHC.dto.response;

import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionDetailResponse {

    UUID transactionDetailId;
    TransactionResponse transaction;
    ProductResponse product;

    Integer quantityChange;
}
