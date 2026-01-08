package com.example.TransportHC.repository;

import com.example.TransportHC.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsProductByName(String name);
}
