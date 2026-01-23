package com.example.TransportHC.dto.request;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import com.example.TransportHC.enums.UserStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {
    @NotBlank(message = "INVALID_USERNAME_PASSWORD")
    @Size(min = 4, max = 20, message = "INVALID_LENGTH_DATA")
    String username;

    @NotBlank(message = "INVALID_USERNAME_PASSWORD")
    @Size(min = 4, max = 20, message = "INVALID_LENGTH_DATA")
    String password;

    @NotBlank(message = "INVALID_INPUT_DATA")
    String fullName;

    @NotBlank(message = "INVALID_INPUT_DATA")
    String address;

    @NotBlank(message = "INVALID_INPUT_DATA")
    String phoneNumber;

    @NotNull(message = "INVALID_INPUT_DATA")
    UserStatus status;

    @NotNull(message = "NULL_DATA_EXCEPTION")
    @Positive(message = "POSITIVE_DATA")
    BigDecimal basicSalary;

    @NotNull(message = "NULL_DATA_EXCEPTION")
    @Positive(message = "POSITIVE_DATA")
    BigDecimal advanceMoney;

    Set<String> roles;
}
