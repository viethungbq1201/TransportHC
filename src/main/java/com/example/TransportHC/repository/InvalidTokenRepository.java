package com.example.TransportHC.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TransportHC.entity.InvalidToken;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {}
