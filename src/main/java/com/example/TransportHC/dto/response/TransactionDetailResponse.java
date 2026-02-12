package com.example.TransportHC.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionDetailResponse {

    Long transactionDetailId;
    TransactionResponse transaction;
    ProductResponse product;

    Integer quantityChange;
}
