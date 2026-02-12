package com.example.TransportHC.configuration;

import java.util.*;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.example.TransportHC.entity.Role;
import com.example.TransportHC.entity.User;
import com.example.TransportHC.repository.RoleRepository;
import com.example.TransportHC.repository.user.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    @Transactional
    ApplicationRunner initAdmin(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                Role adminRole = roleRepository
                        .findByCode("ADMIN")
                        .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin1201"))
                        .fullName("System Administrator")
                        .address("Thái Bình")
                        .phoneNumber("0978791201")
                        .isDriver(false)
                        .roles(Set.of(adminRole))
                        .build();

                userRepository.save(admin);
            }
        };
    }

}
