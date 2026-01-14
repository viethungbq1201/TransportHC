package com.example.TransportHC.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

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
