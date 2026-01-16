package com.example.TransportHC.service;

import com.example.TransportHC.dto.request.ProductCreateRequest;
import com.example.TransportHC.dto.response.CategoryResponse;
import com.example.TransportHC.dto.response.ProductResponse;
import com.example.TransportHC.entity.Category;
import com.example.TransportHC.entity.Inventory;
import com.example.TransportHC.entity.Product;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.CategoryRepository;
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
public class ProductService {

    ProductRepository productRepository;
    InventoryRepository inventoryRepository;
    CategoryRepository categoryRepository;

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public ProductResponse createProduct(ProductCreateRequest request) {

        if (productRepository.existsProductByName(request.getName())) {
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        Product product = Product.builder()
                .name(request.getName())
                .category(category)
                .price(request.getPrice())
                .build();
        productRepository.save(product);

        Inventory inventory = Inventory.builder()
                .product(product)
                .quantity(0)
                .upToDate(LocalDateTime.now())
                .build();
        inventoryRepository.save(inventory);

        product.setInventory(inventory);

        return entityToResponse(product);
    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public List<ProductResponse> viewProduct() {
        return productRepository.findAll().stream()
                .map(this::entityToResponse)
                .toList();
    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public ProductResponse updateProduct(UUID id, ProductCreateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        product.setName(request.getName());
        product.setCategory(category);
        product.setPrice(request.getPrice());
        productRepository.save(product);
        return entityToResponse(product);
    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        productRepository.delete(product);
    }

    ProductResponse entityToResponse(Product product) {
        CategoryResponse category = CategoryResponse.builder()
                .categoryId(product.getProductId())
                .name(product.getName())
                .build();

        return ProductResponse.builder()
                .id(product.getProductId())
                .name(product.getName())
                .category(category)
                .price(product.getPrice())
                .build();
    }

}
