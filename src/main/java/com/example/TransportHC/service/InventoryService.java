package com.example.TransportHC.service;

import com.example.TransportHC.dto.request.InventoryCreateRequest;
import com.example.TransportHC.dto.request.InventoryUpdateRequest;
import com.example.TransportHC.dto.response.InventoryResponse;
import com.example.TransportHC.dto.response.ProductResponse;
import com.example.TransportHC.entity.Inventory;
import com.example.TransportHC.entity.Product;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.InventoryRepository;
import com.example.TransportHC.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryService {
    InventoryRepository inventoryRepository;
    ProductRepository productRepository;

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public InventoryResponse createInventory(InventoryCreateRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Inventory inventory = Inventory.builder()
                .product(product)
                .quantity(request.getQuantity())
                .upToDate(LocalDateTime.now())
                .build();

        inventoryRepository.save(inventory);

        return entityToResponse(inventory);
    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public List<InventoryResponse> viewInventory() {
        return inventoryRepository.findAll().stream()
                .map(this::entityToResponse)
                .toList();
    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public InventoryResponse updateInventory(UUID id, InventoryUpdateRequest request) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->new AppException(ErrorCode.INVENTORY_NOT_FOUND));
        inventory.setQuantity(request.getQuantity());
        inventory.setUpToDate(LocalDateTime.now());
        inventoryRepository.save(inventory);
        return entityToResponse(inventory);
    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public void deleteInventory(UUID id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->new AppException(ErrorCode.INVENTORY_NOT_FOUND));
        inventoryRepository.delete(inventory);
    }

    private InventoryResponse entityToResponse(Inventory inventory) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(inventory.getProduct().getProductId())
                .name(inventory.getProduct().getName())
                .category(inventory.getProduct().getCategory())
                .price(inventory.getProduct().getPrice())
                .build();

        return InventoryResponse.builder()
                .inventoryId(inventory.getInventoryId())
                .product(productResponse)
                .quantity(inventory.getQuantity())
                .upToDate(inventory.getUpToDate())
                .build();
    }
}
