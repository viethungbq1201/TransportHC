package com.example.TransportHC.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.TransportHC.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>, JpaSpecificationExecutor<Inventory> {

    // Eagerly fetch product, product.category, AND product.inventory
    // (bidirectional)
    // to avoid N+1 queries. product.inventory must be included because
    // @OneToOne(mappedBy) cannot be truly lazy-loaded by Hibernate.
    @Override
    @EntityGraph(attributePaths = { "product", "product.category", "product.inventory" })
    List<Inventory> findAll();

    @Override
    @EntityGraph(attributePaths = { "product", "product.category", "product.inventory" })
    Page<Inventory> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = { "product", "product.category", "product.inventory" })
    List<Inventory> findAll(Specification<Inventory> spec);
}
