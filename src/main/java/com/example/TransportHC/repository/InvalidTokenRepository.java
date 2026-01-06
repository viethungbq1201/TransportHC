package com.example.TransportHC.repository;

import com.example.TransportHC.entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {
}
