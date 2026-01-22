package com.example.TransportHC.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.TransportHC.dto.request.CategoryCreateRequest;
import com.example.TransportHC.dto.response.CategoryResponse;
import com.example.TransportHC.entity.Category;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.CategoryRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {

    CategoryRepository categoryRepository;

    @PreAuthorize("hasAuthority('CREATE_CATEGORY')")
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        Category category = Category.builder().name(request.getName()).build();

        categoryRepository.save(category);
        return entityToResponse(category);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('VIEW_CATEGORY')")
    public List<CategoryResponse> viewCategory() {
        return categoryRepository.findAll().stream().map(this::entityToResponse).toList();
    }

    @PreAuthorize("hasAuthority('UPDATE_CATEGORY')")
    public CategoryResponse updateCategory(UUID id, CategoryCreateRequest request) {
        Category category =
                categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        category.setName(request.getName());
        categoryRepository.save(category);
        return entityToResponse(category);
    }

    @PreAuthorize("hasAuthority('DELETE_CATEGORY')")
    public void deleteCategory(UUID id) {
        Category category =
                categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        categoryRepository.delete(category);
    }

    private CategoryResponse entityToResponse(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .build();
    }
}
