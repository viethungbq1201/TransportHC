package com.example.TransportHC.dto.response;

import com.example.TransportHC.enums.UserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    UUID id;
    String username;
    String fullName;
    String address;
    String phoneNumber;
    UserStatus status;
    BigDecimal basicSalary;
    BigDecimal advanceMoney;
    Set<String> roles;

}
