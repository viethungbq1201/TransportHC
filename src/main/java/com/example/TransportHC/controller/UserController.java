package com.example.TransportHC.controller;

import com.example.TransportHC.dto.request.UserCreateRequest;
import com.example.TransportHC.dto.request.UserUpdateRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.dto.response.UserResponse;
import com.example.TransportHC.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/user")
@RestController
public class UserController {
    UserService userService;
    private final RestClient.Builder builder;

    @PostMapping("/createUser")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping("/viewUser")
    ApiResponse<List<UserResponse>> viewUser() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.viewUsers())
                .build();
    }

    @PutMapping("updateUser/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable("userId") UUID id, @RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(id, request))
                .build();
    }

    @DeleteMapping("deleteUser/{userId}")
    String deleteUser(@PathVariable("userId") UUID id) {
        userService.deleteUser(id);
        return "User has been deleted";
    }
}
