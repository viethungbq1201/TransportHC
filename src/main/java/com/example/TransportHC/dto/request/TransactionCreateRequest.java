package com.example.TransportHC.dto.request;

import java.time.LocalDateTime;

import com.example.TransportHC.enums.TransactionType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TransactionCreateRequest {
    TransactionType transactionType;
    LocalDateTime date;
    String location;
    String note;
}
