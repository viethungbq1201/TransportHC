package com.example.TransportHC.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.TransportHC.dto.request.InventoryCreateRequest;
import com.example.TransportHC.dto.request.InventoryFilterRequest;
import com.example.TransportHC.dto.request.InventoryUpdateRequest;
import com.example.TransportHC.dto.response.CategoryResponse;
import com.example.TransportHC.dto.response.InventoryResponse;
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
public class InventoryService {
    InventoryRepository inventoryRepository;
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    @PreAuthorize("hasAuthority('CREATE_INVENTORY')")
    public InventoryResponse createInventory(InventoryCreateRequest request) {
        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Inventory inventory = Inventory.builder()
                .product(product)
                .quantity(request.getQuantity())
                .upToDate(LocalDateTime.now())
                .build();

        inventoryRepository.save(inventory);

        return entityToResponse(inventory);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('VIEW_INVENTORY')")
    public List<InventoryResponse> viewInventory() {
        return inventoryRepository.findAll().stream()
                .map(this::entityToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('VIEW_INVENTORY')")
    public PageResponse<InventoryResponse> viewInventoryPaged(int page, int size) {
        Page<Inventory> inventoryPage = inventoryRepository.findAll(PageRequest.of(page, size));
        List<InventoryResponse> content = inventoryPage.getContent().stream()
                .map(this::entityToResponse)
                .toList();
        return PageResponse.<InventoryResponse>builder()
                .content(content)
                .page(inventoryPage.getNumber())
                .size(inventoryPage.getSize())
                .totalElements(inventoryPage.getTotalElements())
                .totalPages(inventoryPage.getTotalPages())
                .build();
    }

    @PreAuthorize("hasAuthority('FIND_INVENTORY')")
    public InventoryResponse findInventoryById(Long id) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));
        return entityToResponse(inventory);
    }

    @PreAuthorize("hasAuthority('UPDATE_INVENTORY')")
    public InventoryResponse updateInventory(Long id, InventoryUpdateRequest request) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));
        inventory.setQuantity(request.getQuantity());
        inventory.setUpToDate(LocalDateTime.now());
        inventoryRepository.save(inventory);
        return entityToResponse(inventory);
    }

    @PreAuthorize("hasAuthority('DELETE_INVENTORY')")
    public void deleteInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));
        inventoryRepository.delete(inventory);
    }

    @PreAuthorize("hasAuthority('FILTER_INVENTORY')")
    public List<InventoryResponse> filterInventory(InventoryFilterRequest request) {
        return inventoryRepository.findAll(InventorySpecification.filter(request)).stream()
                .map(this::entityToResponse)
                .toList();
    }

    @PreAuthorize("hasAuthority('EXPORT_INVENTORY')")
    public byte[] exportInventoryToExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Inventory");

            // Header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Product Name");
            header.createCell(1).setCellValue("Category");
            header.createCell(2).setCellValue("Quantity");
            header.createCell(3).setCellValue("Price");

            List<Inventory> inventories = inventoryRepository.findAll();

            int rowIdx = 1;
            for (Inventory inv : inventories) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(inv.getProduct().getName());
                row.createCell(1).setCellValue(inv.getProduct().getCategory().getName());
                row.createCell(2).setCellValue(inv.getQuantity());
                row.createCell(3).setCellValue(inv.getProduct().getPrice().doubleValue());
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Export excel failed", e);
        }
    }

    @PreAuthorize("hasAuthority('IMPORT_INVENTORY')")
    public void importInventoryFromExcel(MultipartFile file) {

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // skip header
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                String productName = row.getCell(0).getStringCellValue();
                String categoryName = row.getCell(1).getStringCellValue();
                int quantity = (int) row.getCell(2).getNumericCellValue();
                double price = row.getCell(3).getNumericCellValue();

                // 1️⃣ Find Category
                Category category = categoryRepository
                        .findByNameIgnoreCase(categoryName)
                        .orElseGet(() -> {
                            Category c = Category.builder()
                                    .name(categoryName)
                                    .build();
                            return categoryRepository.save(c);
                        });

                // 2️⃣ Find or create Product
                Product product = productRepository
                        .findByNameIgnoreCase(productName)
                        .orElseGet(() -> {
                            Product p = Product.builder()
                                    .name(productName)
                                    .price(BigDecimal.valueOf(price))
                                    .category(category)
                                    .build();
                            return productRepository.save(p);
                        });

                // 3️⃣ Save Inventory
                Inventory inventory = Inventory.builder()
                        .product(product)
                        .quantity(quantity)
                        .upToDate(LocalDateTime.now())
                        .build();

                inventoryRepository.save(inventory);
            }

        } catch (IOException e) {
            throw new RuntimeException("Import excel failed", e);
        }
    }

    private InventoryResponse entityToResponse(Inventory inventory) {
        CategoryResponse category = CategoryResponse.builder()
                .categoryId(inventory.getProduct().getCategory().getCategoryId())
                .name(inventory.getProduct().getCategory().getName())
                .build();

        ProductResponse productResponse = ProductResponse.builder()
                .id(inventory.getProduct().getProductId())
                .name(inventory.getProduct().getName())
                .category(category)
                .price(inventory.getProduct().getPrice())
                .build();

        return InventoryResponse.builder()
                .inventoryId(inventory.getInventoryId())
                .product(productResponse)
                .quantity(inventory.getQuantity())
                .inTransit(inventory.getInTransit())
                .upToDate(inventory.getUpToDate())
                .build();
    }
}
