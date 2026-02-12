package com.example.TransportHC.dto.response;

import java.time.LocalDateTime;

import com.example.TransportHC.enums.ApproveStatus;
import com.example.TransportHC.enums.TransactionType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponse {

    Long transactionId;
    TransactionType transactionType;
    LocalDateTime date;
    String location;
    String note;
    ApproveStatus approveStatus;

    UserResponse createdBy;
}
