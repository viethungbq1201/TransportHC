package com.example.TransportHC.repository;

import com.example.TransportHC.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, String> {

}
