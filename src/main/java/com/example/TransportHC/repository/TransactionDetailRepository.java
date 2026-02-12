package com.example.TransportHC.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TransportHC.entity.TransactionDetail;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {
}
