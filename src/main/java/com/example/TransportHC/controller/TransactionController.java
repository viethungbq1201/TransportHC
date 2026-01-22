package com.example.TransportHC.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.TransportHC.dto.request.TransactionCreateRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.dto.response.TransactionResponse;
import com.example.TransportHC.service.TransactionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/transaction")
@RestController
public class TransactionController {

    TransactionService transactionService;

    @PostMapping("/createTransaction")
    ApiResponse<TransactionResponse> createTransaction(@RequestBody @Valid TransactionCreateRequest request) {
        return ApiResponse.<TransactionResponse>builder()
                .result(transactionService.createTransaction(request))
                .build();
    }

    @GetMapping("/viewTransaction")
    ApiResponse<List<TransactionResponse>> viewTransactions() {
        return ApiResponse.<List<TransactionResponse>>builder()
                .result(transactionService.viewTransaction())
                .build();
    }

    @PutMapping("/updateTransaction/{transactionId}")
    ApiResponse<TransactionResponse> updateTransaction(
            @PathVariable("transactionId") UUID id, @RequestBody @Valid TransactionCreateRequest request) {
        return ApiResponse.<TransactionResponse>builder()
                .result(transactionService.updateTransaction(id, request))
                .build();
    }

    @DeleteMapping("/deleteTransaction/{transactionId}")
    ApiResponse<String> deleteTransaction(@PathVariable("transactionId") UUID id) {
        transactionService.deleteTransaction(id);
        return ApiResponse.<String>builder()
                .result("Transaction have been deleted")
                .build();
    }

    @PutMapping("/approveTransaction/{transactionId}")
    ApiResponse<TransactionResponse> approveTransaction(@PathVariable("transactionId") UUID id) {
        return ApiResponse.<TransactionResponse>builder()
                .result(transactionService.approveTransaction(id))
                .build();
    }

    @PutMapping("/rejectTransaction/{transactionId}")
    ApiResponse<TransactionResponse> rejectTransaction(@PathVariable("transactionId") UUID id) {
        return ApiResponse.<TransactionResponse>builder()
                .result(transactionService.rejectTransaction(id))
                .build();
    }
}
