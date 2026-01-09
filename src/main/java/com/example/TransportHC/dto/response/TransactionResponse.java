package com.example.TransportHC.dto.response;

import com.example.TransportHC.enums.TransactionType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponse {

    UUID transactionId;
    TransactionType transactionType;
    LocalDateTime date;
    String location;
    String note;

    UserResponse createdBy;
}
