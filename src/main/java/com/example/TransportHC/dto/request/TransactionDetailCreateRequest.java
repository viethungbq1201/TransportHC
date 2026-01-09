package com.example.TransportHC.dto.request;

import com.example.TransportHC.dto.response.ProductResponse;
import com.example.TransportHC.dto.response.TransactionResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

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
