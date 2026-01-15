package com.example.TransportHC.dto.request;

import com.example.TransportHC.enums.UserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateStatusRequest {
    UserStatus status;
}
