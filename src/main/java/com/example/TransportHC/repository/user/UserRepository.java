package com.example.TransportHC.repository.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TransportHC.entity.User;

public interface UserRepository extends JpaRepository<User, UUID>, UserRepositoryCustom {

    java.util.Optional<User> findUserByUsername(String username);

    boolean existsByUsername(String username);
}
