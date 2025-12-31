package com.example.TransportHC.repository;

import com.example.TransportHC.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}
