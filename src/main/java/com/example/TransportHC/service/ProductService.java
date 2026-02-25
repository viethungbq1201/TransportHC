package com.example.TransportHC.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.TransportHC.dto.request.ProductCreateRequest;
import com.example.TransportHC.dto.response.CategoryResponse;
import com.example.TransportHC.dto.response.PageResponse;
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

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

        ProductRepository productRepository;
        InventoryRepository inventoryRepository;
        CategoryRepository categoryRepository;

        @PreAuthorize("hasAuthority('CREATE_PRODUCT')")
        public ProductResponse createProduct(ProductCreateRequest request) {

                if (productRepository.existsProductByName(request.getName())) {
                        throw new AppException(ErrorCode.PRODUCT_EXISTED);
                }

                Category category = categoryRepository
                                .findById(request.getCategoryId())
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
                                .inTransit(0)
                                .upToDate(LocalDateTime.now())
                                .build();
                inventoryRepository.save(inventory);

                product.setInventory(inventory);

                return entityToResponse(product);
        }

        @Transactional(readOnly = true)
        @PreAuthorize("hasAuthority('VIEW_PRODUCT')")
        public List<ProductResponse> viewProduct() {
                return productRepository.findAll().stream().map(this::entityToResponse).toList();
        }

        @Transactional(readOnly = true)
        @PreAuthorize("hasAuthority('VIEW_PRODUCT')")
        public PageResponse<ProductResponse> viewProductPaged(int page, int size) {
                Page<Product> productPage = productRepository.findAll(PageRequest.of(page, size));
                List<ProductResponse> content = productPage.getContent().stream()
                                .map(this::entityToResponse)
                                .toList();
                return PageResponse.<ProductResponse>builder()
                                .content(content)
                                .page(productPage.getNumber())
                                .size(productPage.getSize())
                                .totalElements(productPage.getTotalElements())
                                .totalPages(productPage.getTotalPages())
                                .build();
        }

        @PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
        public ProductResponse updateProduct(Long id, ProductCreateRequest request) {
                Product product = productRepository.findById(id)
                                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
                Category category = categoryRepository
                                .findById(request.getCategoryId())
                                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

                product.setName(request.getName());
                product.setCategory(category);
                product.setPrice(request.getPrice());
                productRepository.save(product);
                return entityToResponse(product);
        }

        @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
        public void deleteProduct(Long id) {
                Product product = productRepository.findById(id)
                                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

                productRepository.delete(product);
        }

        ProductResponse entityToResponse(Product product) {
                CategoryResponse category = CategoryResponse.builder()
                                .categoryId(product.getCategory().getCategoryId())
                                .name(product.getCategory().getName())
                                .build();

                return ProductResponse.builder()
                                .id(product.getProductId())
                                .name(product.getName())
                                .category(category)
                                .price(product.getPrice())
                                .build();
        }
}
