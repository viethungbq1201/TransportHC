package com.example.TransportHC.dto.request;

import java.math.BigDecimal;
import java.util.Set;

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
    @Size(min = 4, message = "INVALID_USERNAME")
    String username;

    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;

    String fullName;
    String address;
    String phoneNumber;
    UserStatus status;
    BigDecimal basicSalary;
    BigDecimal advanceMoney;
    Set<String> roles;
}
