package com.example.TransportHC.controller;

import java.util.List;


import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.TransportHC.dto.request.ProductCreateRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.dto.response.ProductResponse;
import com.example.TransportHC.service.ProductService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/product")
@RestController
public class ProductController {

    ProductService productService;

    @PostMapping("/createProduct")
    ApiResponse<ProductResponse> createProduct(@RequestBody @Valid ProductCreateRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .build();
    }

    @GetMapping("/viewProduct")
    ApiResponse<List<ProductResponse>> viewProduct() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.viewProduct())
                .build();
    }

    @PutMapping("/updateProduct/{productId}")
    ApiResponse<ProductResponse> updateProduct(
            @PathVariable("productId") Long id, @RequestBody @Valid ProductCreateRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(id, request))
                .build();
    }

    @DeleteMapping("/deleteProduct/{productId}")
    ApiResponse<String> deleteProduct(@PathVariable("productId") Long id) {
        productService.deleteProduct(id);
        return ApiResponse.<String>builder().result("Product have been deleted").build();
    }
}
