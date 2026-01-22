package com.example.TransportHC.dto.request;

import jakarta.validation.constraints.NotBlank;

import com.example.TransportHC.enums.UserStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateStatusRequest {
    @NotBlank(message = "INVALID_INPUT_DATA")
    UserStatus status;
}
