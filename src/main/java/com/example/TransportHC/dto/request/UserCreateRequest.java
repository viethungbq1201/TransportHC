package com.example.TransportHC.dto.request;

import com.example.TransportHC.enums.UserStatus;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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
