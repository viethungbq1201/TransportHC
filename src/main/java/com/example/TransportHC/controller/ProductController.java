package com.example.TransportHC.controller;

import com.example.TransportHC.dto.request.ProductCreateRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.dto.response.ProductResponse;
import com.example.TransportHC.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/product")
@RestController
public class ProductController {

    ProductService productService;

    @PostMapping("/createProduct")
    ApiResponse<ProductResponse> createProduct (@RequestBody ProductCreateRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .build();
    }

    @GetMapping("/viewProduct")
    ApiResponse<List<ProductResponse>> viewProduct () {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.viewProduct())
                .build();
    }

    @PutMapping("/updateProduct/{productId}")
    ApiResponse<ProductResponse> updateProduct (@PathVariable("productId") UUID id, @RequestBody ProductCreateRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(id, request))
                .build();
    }

    @DeleteMapping("/deleteProduct/{productId}")
    String deleteProduct (@PathVariable("productId") UUID id) {
        productService.deleteProduct(id);
        return "Product have been deleted";
    }

}
