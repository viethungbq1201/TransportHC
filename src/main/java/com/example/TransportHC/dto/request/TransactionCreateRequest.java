package com.example.TransportHC.dto.request;

import com.example.TransportHC.dto.response.UserResponse;
import com.example.TransportHC.entity.User;
import com.example.TransportHC.enums.TransactionType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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

    UserResponse createdBy;
}
