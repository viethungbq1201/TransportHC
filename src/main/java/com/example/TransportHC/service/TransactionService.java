package com.example.TransportHC.service;

import com.example.TransportHC.dto.request.TransactionCreateRequest;
import com.example.TransportHC.dto.response.TransactionResponse;
import com.example.TransportHC.dto.response.UserResponse;
import com.example.TransportHC.entity.Role;
import com.example.TransportHC.entity.Transaction;
import com.example.TransportHC.entity.User;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.TransactionRepository;
import com.example.TransportHC.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionService {
    TransactionRepository transactionRepository;
    UserRepository userRepository;

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public TransactionResponse createTransaction(TransactionCreateRequest request) {
        Transaction transaction = Transaction.builder()
                .type(request.getTransactionType())
                .date(LocalDateTime.now())
                .location(request.getLocation())
                .note(request.getNote())
                .build();

        var context =  SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        transaction.setCreatedBy(user);
        transactionRepository.save(transaction);
        return entityToDto(transaction);
    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public List<TransactionResponse> viewTransaction() {
        return transactionRepository.findAll().stream()
                .map(this::entityToDto)
                .toList();
    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public TransactionResponse updateTransaction(UUID id, TransactionCreateRequest request) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        transaction.setType(request.getTransactionType());
        transaction.setDate(LocalDateTime.now());
        transaction.setNote(request.getNote());
        transaction.setLocation(request.getLocation());

        transactionRepository.save(transaction);
        return entityToDto(transaction);
    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public void deleteTransaction(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));
        transactionRepository.delete(transaction);
    }

    private TransactionResponse entityToDto(Transaction transaction) {
        // Chuyển đổi Set<Role> sang Set<String> (role codes)
        Set<String> roleCodes = transaction.getCreatedBy().getRoles() != null
                ? transaction.getCreatedBy().getRoles().stream()
                .map(Role::getCode)
                .collect(Collectors.toSet()): new HashSet<>();

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
                .createdBy(userResponse)
                .build();
    }
}
