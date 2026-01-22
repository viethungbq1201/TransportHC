package com.example.TransportHC.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.TransportHC.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByUsername(String username);

    boolean existsByUsername(String username);

    @Query("""
		SELECT DISTINCT u
		FROM User u
		JOIN u.roles r
		WHERE r.code <> 'ADMIN'
	""")
    List<User> findAllNonAdminUsers();
}
