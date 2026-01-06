package com.example.TransportHC.repository;

import com.example.TransportHC.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

}
