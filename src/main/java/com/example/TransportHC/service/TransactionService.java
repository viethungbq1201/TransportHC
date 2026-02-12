package com.example.TransportHC.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.TransportHC.dto.request.TransactionCreateRequest;
import com.example.TransportHC.dto.response.TransactionResponse;
import com.example.TransportHC.dto.response.UserResponse;
import com.example.TransportHC.entity.Role;
import com.example.TransportHC.entity.Transaction;
import com.example.TransportHC.entity.User;
import com.example.TransportHC.enums.ApproveStatus;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.TransactionRepository;
import com.example.TransportHC.repository.user.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionService {
    TransactionRepository transactionRepository;
    UserRepository userRepository;

    @PreAuthorize("hasAuthority('CREATE_TRANSACTION')")
    public TransactionResponse createTransaction(TransactionCreateRequest request) {
        Transaction transaction = Transaction.builder()
                .type(request.getTransactionType())
                .date(LocalDateTime.now())
                .location(request.getLocation())
                .note(request.getNote())
                .approveStatus(ApproveStatus.PENDING)
                .build();

        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository
                .findUserByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        transaction.setCreatedBy(user);
        transactionRepository.save(transaction);
        return entityToResponse(transaction);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('VIEW_TRANSACTION')")
    public List<TransactionResponse> viewTransaction() {
        return transactionRepository.findAll().stream()
                .map(this::entityToResponse)
                .toList();
    }

    @PreAuthorize("hasAuthority('UPDATE_TRANSACTION')")
    public TransactionResponse updateTransaction(Long id, TransactionCreateRequest request) {
        Transaction transaction =
                transactionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        if (transaction.getApproveStatus() != ApproveStatus.PENDING) {
            throw new AppException(ErrorCode.TRANSACTION_ALREADY_APPROVED);
        }

        transaction.setType(request.getTransactionType());
        transaction.setDate(LocalDateTime.now());
        transaction.setNote(request.getNote());
        transaction.setLocation(request.getLocation());

        transactionRepository.save(transaction);
        return entityToResponse(transaction);
    }

    @PreAuthorize("hasAuthority('DELETE_TRANSACTION')")
    public void deleteTransaction(Long id) {
        Transaction transaction =
                transactionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        if (transaction.getApproveStatus() != ApproveStatus.PENDING) {
            throw new AppException(ErrorCode.TRANSACTION_ALREADY_APPROVED);
        }

        if (transaction.getSchedule() != null) {
            throw new AppException(ErrorCode.TRANSACTION_ALREADY_USED);
        }

        transactionRepository.delete(transaction);
    }

    @PreAuthorize("hasAuthority('APPROVE_TRANSACTION')")
    public TransactionResponse approveTransaction(Long id) {
        Transaction transaction =
                transactionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        if (transaction.getApproveStatus() != ApproveStatus.PENDING) {
            throw new AppException(ErrorCode.TRANSACTION_ALREADY_APPROVED);
        }

        transaction.setApproveStatus(ApproveStatus.APPROVED);
        transactionRepository.save(transaction);

        return entityToResponse(transaction);
    }

    @PreAuthorize("hasAuthority('REJECT_TRANSACTION')")
    public TransactionResponse rejectTransaction(Long id) {
        Transaction transaction =
                transactionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        if (transaction.getApproveStatus() != ApproveStatus.PENDING) {
            throw new AppException(ErrorCode.TRANSACTION_ALREADY_APPROVED);
        }

        transaction.setApproveStatus(ApproveStatus.REJECTED);
        transactionRepository.save(transaction);

        return entityToResponse(transaction);
    }

    private TransactionResponse entityToResponse(Transaction transaction) {
        // Chuyển đổi Set<Role> sang Set<String> (role codes)
        Set<String> roleCodes = transaction.getCreatedBy().getRoles() != null
                ? transaction.getCreatedBy().getRoles().stream()
                        .map(Role::getCode)
                        .collect(Collectors.toSet())
                : new HashSet<>();

        UserResponse userResponse = UserResponse.builder()
                .id(transaction.getCreatedBy().getUserId())
                .username(transaction.getCreatedBy().getUsername())
                .fullName(transaction.getCreatedBy().getFullName())
                .address(transaction.getCreatedBy().getAddress())
                .phoneNumber(String.valueOf(transaction.getCreatedBy().getPhoneNumber()))
                .status(transaction.getCreatedBy().getStatus())
                .basicSalary(transaction.getCreatedBy().getBasicSalary())
                .advanceMoney(transaction.getCreatedBy().getAdvanceMoney())
                .roles(roleCodes)
                .build();

        return TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .transactionType(transaction.getType())
                .date(transaction.getDate())
                .location(transaction.getLocation())
                .note(transaction.getNote())
                .approveStatus(transaction.getApproveStatus())
                .createdBy(userResponse)
                .build();
    }
}
