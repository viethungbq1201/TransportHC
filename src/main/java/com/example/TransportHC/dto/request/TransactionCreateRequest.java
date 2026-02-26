package com.example.TransportHC.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.example.TransportHC.enums.TransactionType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TransactionCreateRequest {
    @NotNull(message = "NULL_DATA_EXCEPTION")
    TransactionType transactionType;

    LocalDateTime date;

    @NotNull(message = "NULL_DATA_EXCEPTION")
    @Size(min = 4, max = 255, message = "INVALID_LENGTH_DATA")
    String location;

    String note;
}
