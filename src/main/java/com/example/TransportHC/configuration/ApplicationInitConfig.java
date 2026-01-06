package com.example.TransportHC.configuration;

import com.example.TransportHC.entity.Permission;
import com.example.TransportHC.entity.Role;
import com.example.TransportHC.entity.User;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.repository.PermissionRepository;
import com.example.TransportHC.repository.RoleRepository;
import com.example.TransportHC.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    @Transactional
    ApplicationRunner initData(
            PermissionRepository permissionRepository,
            RoleRepository roleRepository,
            UserRepository userRepository
    ) {
        return args -> {

            // Danh sách các permission cần tạo
            Map<String, String> permissionMap = Map.of(
                    "CREATE_COST", "Create cost",
                    "APPROVE_COST", "Approve cost",
                    "APPROVE_SCHEDULE", "Approve schedule"
            );

            // Tạo/tìm permissions (chỉ tạo mới nếu chưa tồn tại)
            List<Permission> allPermissions = new ArrayList<>();
            for (Map.Entry<String, String> entry : permissionMap.entrySet()) {
                String code = entry.getKey();
                String name = entry.getValue();

                Permission permission = permissionRepository.findByCode(code)
                        .orElseGet(() -> {
                            Permission newPermission = Permission.builder()
                                    .code(code)
                                    .name(name)
                                    .build();
                            return permissionRepository.save(newPermission);
                        });

                allPermissions.add(permission);
            }

            // Tìm từng permission theo code
            Permission createCost = allPermissions.stream()
                    .filter(p -> "CREATE_COST".equals(p.getCode()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy permission CREATE_COST"));

            Permission approveCost = allPermissions.stream()
                    .filter(p -> "APPROVE_COST".equals(p.getCode()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy permission APPROVE_COST"));

            Permission approveSchedule = allPermissions.stream()
                    .filter(p -> "APPROVE_SCHEDULE".equals(p.getCode()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy permission APPROVE_SCHEDULE"));

            // Tạo role ADMIN với permissions
            Set<Permission> adminPermissions = new HashSet<>();
            adminPermissions.add(createCost);
            adminPermissions.add(approveCost);
            adminPermissions.add(approveSchedule);

            Role adminRole = roleRepository.findByCode("ADMIN")
                    .map(existingRole -> {
                        // Kiểm tra xem permissions đã đủ chưa, nếu chưa thì cập nhật
                        if (!existingRole.getPermissions().containsAll(adminPermissions)) {
                            existingRole.getPermissions().clear();
                            existingRole.getPermissions().addAll(adminPermissions);
                            return roleRepository.save(existingRole);
                        }
                        return existingRole;
                    })
                    .orElseGet(() -> {
                        Role role = Role.builder()
                                .code("ADMIN")
                                .name("Administrator")
                                .permissions(adminPermissions)
                                .build();
                        return roleRepository.save(role);
                    });


            // Tạo role DRIVER với permission CREATE_COST
            Set<Permission> driverPermissions = new HashSet<>();
            driverPermissions.add(createCost);

            Role driverRole = roleRepository.findByCode("DRIVER")
                    .map(existingRole -> {
                        if (!existingRole.getPermissions().containsAll(driverPermissions)) {
                            existingRole.getPermissions().clear();
                            existingRole.getPermissions().addAll(driverPermissions);
                            return roleRepository.save(existingRole);
                        }
                        return existingRole;
                    })
                    .orElseGet(() -> {
                        Role role = Role.builder()
                                .code("DRIVER")
                                .name("Driver")
                                .permissions(driverPermissions)
                                .build();
                        return roleRepository.save(role);
                    });


            // Tạo user admin nếu chưa tồn tại
            if (!userRepository.existsByUsername("admin")) {
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);

                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin1201"))
                        .fullname("Admin Viet Hung")
                        .roles(roles)
                        .build();

                userRepository.save(admin);
            }
        };
    }
}
