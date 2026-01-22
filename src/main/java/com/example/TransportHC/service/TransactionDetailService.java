package com.example.TransportHC.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.TransportHC.dto.request.TransactionDetailCreateRequest;
import com.example.TransportHC.dto.response.*;
import com.example.TransportHC.entity.*;
import com.example.TransportHC.enums.ApproveStatus;
import com.example.TransportHC.enums.TransactionType;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.ProductRepository;
import com.example.TransportHC.repository.TransactionDetailRepository;
import com.example.TransportHC.repository.TransactionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionDetailService {
    TransactionRepository transactionRepository;
    TransactionDetailRepository transactionDetailRepository;
    ProductRepository productRepository;

    @PreAuthorize("hasAuthority('CREATE_TRANSACTION_DETAIL')")
    public TransactionDetailResponse createTransactionResponse(TransactionDetailCreateRequest request) {
        Transaction transaction = transactionRepository
                .findById(request.getTransactionId())
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        if (transaction.getApproveStatus() != ApproveStatus.PENDING) {
            throw new AppException(ErrorCode.TRANSACTION_ALREADY_APPROVED);
        }

        TransactionDetail transactionDetail = TransactionDetail.builder()
                .transaction(transaction)
                .product(product)
                .quantityChange(request.getQuantityChange())
                .build();

        validateInventory(transactionDetail);
        transactionDetailRepository.save(transactionDetail);
        return entityToResponse(transactionDetail);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('VIEW_TRANSACTION_DETAIL')")
    public List<TransactionDetailResponse> viewTransactionDetailResponse() {
        return transactionDetailRepository.findAll().stream()
                .map(this::entityToResponse)
                .toList();
    }

    @PreAuthorize("hasAuthority('UPDATE_TRANSACTION_DETAIL')")
    public TransactionDetailResponse updateTransactionDetail(UUID id, TransactionDetailCreateRequest request) {
        TransactionDetail transactionDetail = transactionDetailRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_DETAIL_NOT_FOUND));

        Transaction transaction = transactionRepository
                .findById(request.getTransactionId())
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        if (transaction.getApproveStatus() != ApproveStatus.PENDING) {
            throw new AppException(ErrorCode.TRANSACTION_ALREADY_APPROVED);
        }

        transactionDetail.setQuantityChange(request.getQuantityChange());
        transactionDetail.setTransaction(transaction);
        transactionDetail.setProduct(product);
        transactionDetail.getProduct().getInventory().setUpToDate(LocalDateTime.now());

        validateInventory(transactionDetail);
        transactionDetailRepository.save(transactionDetail);

        return entityToResponse(transactionDetail);
    }

    @PreAuthorize("hasAuthority('DELETE_TRANSACTION_DETAIL')")
    public void deleteTransactionDetail(UUID id) {
        TransactionDetail transactionDetail = transactionDetailRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_DETAIL_NOT_FOUND));

        transactionDetailRepository.delete(transactionDetail);
    }

    private void validateInventory(TransactionDetail transactionDetail) {
        if (transactionDetail.getTransaction().getType() == TransactionType.OUT) {
            int current = transactionDetail.getProduct().getInventory().getQuantity();
            int transit = transactionDetail.getProduct().getInventory().getInTransit();
            int available = current - transit;
            if (available < transactionDetail.getQuantityChange()) {
                throw new AppException(ErrorCode.INVENTORY_NOT_ENOUGH);
            }
        }
    }

    private TransactionDetailResponse entityToResponse(TransactionDetail transactionDetail) {
        // Chuyển đổi Set<Role> sang Set<String> (role codes)
        Set<String> roleCodes =
                transactionDetail.getTransaction().getCreatedBy().getRoles() != null
                        ? transactionDetail.getTransaction().getCreatedBy().getRoles().stream()
                                .map(Role::getCode)
                                .collect(Collectors.toSet())
                        : new HashSet<>();

        UserResponse userResponse = UserResponse.builder()
                .id(transactionDetail.getTransaction().getCreatedBy().getUserId())
                .username(transactionDetail.getTransaction().getCreatedBy().getUsername())
                .fullName(transactionDetail.getTransaction().getCreatedBy().getFullName())
                .address(transactionDetail.getTransaction().getCreatedBy().getAddress())
                .phoneNumber(String.valueOf(
                        transactionDetail.getTransaction().getCreatedBy().getPhoneNumber()))
                .status(transactionDetail.getTransaction().getCreatedBy().getStatus())
                .basicSalary(transactionDetail.getTransaction().getCreatedBy().getBasicSalary())
                .advanceMoney(transactionDetail.getTransaction().getCreatedBy().getAdvanceMoney())
                .roles(roleCodes)
                .build();

        TransactionResponse transactionResponse = TransactionResponse.builder()
                .transactionId(transactionDetail.getTransaction().getTransactionId())
                .transactionType(transactionDetail.getTransaction().getType())
                .date(transactionDetail.getTransaction().getDate())
                .location(transactionDetail.getTransaction().getLocation())
                .note(transactionDetail.getTransaction().getNote())
                .approveStatus(transactionDetail.getTransaction().getApproveStatus())
                .createdBy(userResponse)
                .build();

        CategoryResponse categoryResponse = CategoryResponse.builder()
                .categoryId(transactionDetail.getProduct().getCategory().getCategoryId())
                .name(transactionDetail.getProduct().getCategory().getName())
                .build();

        ProductResponse productResponse = ProductResponse.builder()
                .id(transactionDetail.getProduct().getProductId())
                .name(transactionDetail.getProduct().getName())
                .category(categoryResponse)
                .price(transactionDetail.getProduct().getPrice())
                .build();

        return TransactionDetailResponse.builder()
                .transactionDetailId(transactionDetail.getId())
                .transaction(transactionResponse)
                .product(productResponse)
                .quantityChange(transactionDetail.getQuantityChange())
                .build();
    }
}
