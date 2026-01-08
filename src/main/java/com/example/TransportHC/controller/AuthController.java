package com.example.TransportHC.controller;

import com.example.TransportHC.dto.request.AuthLoginRequest;
import com.example.TransportHC.dto.request.LogoutRequest;
import com.example.TransportHC.dto.request.RefreshRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.dto.response.AuthResponse;
import com.example.TransportHC.entity.User;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.UserRepository;
import com.example.TransportHC.service.AuthService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/auth")
@RestController
public class AuthController {
    AuthService authService;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    AuthResponse login(@RequestBody AuthLoginRequest request) {
        var user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.INVALID_USERNAME_PASSWORD);
        }

        String token = authService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @PostMapping("/logout")
    void logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authService.logout(request);
    }

    @PostMapping("/refresh")
    AuthResponse introspect(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        return authService.refreshToken(request);

    }



}
