package com.example.TransportHC.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AuthLoginRequest {
    @NotBlank(message = "INVALID_USERNAME_PASSWORD")
    @Size(min = 4, max = 20, message = "INVALID_LENGTH_DATA")
    String username;

    @NotBlank(message = "INVALID_USERNAME_PASSWORD")
    @Size(min = 4, max = 20, message = "INVALID_LENGTH_DATA")
    String password;
}
