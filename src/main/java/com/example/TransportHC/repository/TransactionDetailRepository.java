package com.example.TransportHC.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TransportHC.entity.TransactionDetail;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {

    // Eagerly fetch everything accessed by the DTO mapper + mappedBy associations:
    // - transaction (and its createdBy, roles, schedule)
    // - product (and its category, inventory)
    @Override
    @EntityGraph(attributePaths = {
            "transaction",
            "transaction.createdBy",
            "transaction.createdBy.roles",
            "transaction.schedule",
            "product",
            "product.category",
            "product.inventory"
    })
    List<TransactionDetail> findAll();

    @Override
    @EntityGraph(attributePaths = {
            "transaction",
            "transaction.createdBy",
            "transaction.createdBy.roles",
            "transaction.schedule",
            "product",
            "product.category",
            "product.inventory"
    })
    Page<TransactionDetail> findAll(Pageable pageable);
}
