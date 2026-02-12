package com.example.TransportHC.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TransportHC.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsProductByName(String name);

    Optional<Product> findByNameIgnoreCase(String name);
}
