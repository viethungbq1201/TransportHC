package com.example.TransportHC.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.TransportHC.dto.request.CategoryCreateRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.dto.response.CategoryResponse;
import com.example.TransportHC.service.CategoryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/category")
@RestController
public class CategoryController {
    CategoryService categoryService;

    @PostMapping("/createCategory")
    ApiResponse<CategoryResponse> createCategory(@RequestBody @Valid CategoryCreateRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.createCategory(request))
                .build();
    }

    @GetMapping("/viewCategory")
    ApiResponse<List<CategoryResponse>> viewCategory() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.viewCategory())
                .build();
    }

    @PutMapping("/updateCategory/{categoryId}")
    ApiResponse<CategoryResponse> updateCategory(
            @PathVariable("categoryId") UUID id, @RequestBody @Valid CategoryCreateRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updateCategory(id, request))
                .build();
    }

    @DeleteMapping("/delelteCategory/{categoryId}")
    ApiResponse<String> deleteCategory(@PathVariable("categoryId") UUID id) {
        categoryService.deleteCategory(id);
        return ApiResponse.<String>builder()
                .result("Cost Type have been delete")
                .build();
    }
}
