package com.example.TransportHC.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TransportHC.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Eagerly fetch relations to prevent N+1:
    // - createdBy and createdBy.roles (accessed in DTO)
    // - schedule (OneToOne mappedBy that cannot be lazy loaded safely)
    @Override
    @EntityGraph(attributePaths = { "createdBy", "createdBy.roles", "schedule" })
    List<Transaction> findAll();

    @Override
    @EntityGraph(attributePaths = { "createdBy", "createdBy.roles", "schedule" })
    Page<Transaction> findAll(Pageable pageable);
}
