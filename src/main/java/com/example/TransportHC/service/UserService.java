package com.example.TransportHC.service;

import com.example.TransportHC.dto.request.UserCreateRequest;
import com.example.TransportHC.dto.request.UserUpdateRequest;
import com.example.TransportHC.dto.request.UserUpdateStatusRequest;
import com.example.TransportHC.dto.response.UserResponse;
import com.example.TransportHC.entity.Role;
import com.example.TransportHC.entity.User;

import com.example.TransportHC.enums.UserStatus;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.RoleRepository;
import com.example.TransportHC.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public UserResponse createUser (UserCreateRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .address(request.getAddress())
                .phoneNumber(String.valueOf(request.getPhoneNumber()))
                .basicSalary(request.getBasicSalary())
                .advanceMoney(request.getAdvanceMoney())
                .build();

        if (request.getStatus() != null) {
            user.setStatus(request.getStatus());
        } else {
            user.setStatus(UserStatus.AVAILABLE);
        }

        Set<Role> roles = new HashSet<>();
        String roleCode;
        boolean isDriver = false;

        if (request.getRoles().contains("MANAGER")) {
            roleCode = "MANAGER";
        } else if (request.getRoles().contains("ACCOUNTANT")) {
            roleCode = "ACCOUNTANT";
        } else {
            roleCode = "DRIVER";
            isDriver = true;
        }
        Role assignedRole = roleRepository.findByCode(roleCode)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        roles.add(assignedRole);
        user.setRoles(roles);
        user.setIsDriver(isDriver);

        userRepository.save(user);

        return entityToResponse(user);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('CREATE_COST')")
    public List<UserResponse> viewUsers() {
        return userRepository.findAll().stream()
                .map(this::entityToResponse)
                .toList();

    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public UserResponse updateUser (UUID id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setAddress(request.getAddress());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setStatus(request.getStatus());
        user.setBasicSalary(request.getBasicSalary());
        user.setAdvanceMoney(request.getAdvanceMoney());

        Set<Role> roles = new HashSet<>();
        String roleCode;
        boolean isDriver = false;

        if (request.getRoles().contains("MANAGER")) {
            roleCode = "MANAGER";
        } else if (request.getRoles().contains("ACCOUNTANT")) {
            roleCode = "ACCOUNTANT";
        } else {
            roleCode = "DRIVER";
            isDriver = true;
        }
        Role assignedRole = roleRepository.findByCode(roleCode)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        roles.add(assignedRole);
        user.setRoles(roles);
        user.setIsDriver(isDriver);

        userRepository.save(user);

        return entityToResponse(user);
    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public UserResponse updateStatusUser (UUID id, UserUpdateStatusRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        user.setStatus(request.getStatus());

        userRepository.save(user);

        return entityToResponse(user);
    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public void deleteUser (UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    private UserResponse entityToResponse(User user) {
        // Chuyển đổi Set<Role> sang Set<String> (role codes)
        Set<String> roleCodes = user.getRoles() != null
                ? user.getRoles().stream()
                .map(Role::getCode)
                .collect(Collectors.toSet()): new HashSet<>();

        return UserResponse.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .address(user.getAddress())
                .phoneNumber(String.valueOf(user.getPhoneNumber()))
                .status(user.getStatus())
                .basicSalary(user.getBasicSalary())
                .advanceMoney(user.getAdvanceMoney())
                .roles(roleCodes)
                .build();
    }

}
