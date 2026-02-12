package com.example.TransportHC.dto.response;

import java.math.BigDecimal;
import java.util.Set;

import com.example.TransportHC.enums.UserStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String username;
    String fullName;
    String address;
    String phoneNumber;
    UserStatus status;
    BigDecimal basicSalary;
    BigDecimal advanceMoney;
    Set<String> roles;
}
