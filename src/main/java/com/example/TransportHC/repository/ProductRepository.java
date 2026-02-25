package com.example.TransportHC.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TransportHC.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsProductByName(String name);

    Optional<Product> findByNameIgnoreCase(String name);

    // Eagerly fetch category and inventory to avoid N+1.
    // inventory must be included because @OneToOne(mappedBy)
    // cannot be truly lazy-loaded by Hibernate.
    @Override
    @EntityGraph(attributePaths = { "category", "inventory" })
    List<Product> findAll();

    @Override
    @EntityGraph(attributePaths = { "category", "inventory" })
    Page<Product> findAll(Pageable pageable);
}
