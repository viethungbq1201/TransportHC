package com.example.TransportHC.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TransportHC.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {}
