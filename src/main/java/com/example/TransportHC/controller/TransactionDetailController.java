package com.example.TransportHC.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.example.TransportHC.dto.request.TransactionDetailCreateRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.dto.response.TransactionDetailResponse;
import com.example.TransportHC.service.TransactionDetailService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/transactiondetail")
@RestController
public class TransactionDetailController {

    TransactionDetailService transactionDetailService;

    @PostMapping("/createTransactionDetail")
    ApiResponse<TransactionDetailResponse> createTransactionDetail(
            @RequestBody TransactionDetailCreateRequest request) {
        return ApiResponse.<TransactionDetailResponse>builder()
                .result(transactionDetailService.createTransactionResponse(request))
                .build();
    }

    @GetMapping("/viewTransactionDetail")
    ApiResponse<List<TransactionDetailResponse>> viewTransactionDetail() {
        return ApiResponse.<List<TransactionDetailResponse>>builder()
                .result(transactionDetailService.viewTransactionDetailResponse())
                .build();
    }

    @PutMapping("/updateTransactionDetail/{TDid}")
    ApiResponse<TransactionDetailResponse> updateTransactionDetail(
            @PathVariable("TDid") UUID id, @RequestBody TransactionDetailCreateRequest request) {
        return ApiResponse.<TransactionDetailResponse>builder()
                .result(transactionDetailService.updateTransactionDetail(id, request))
                .build();
    }

    @DeleteMapping("/deleteTransactionDetail/{TDid}")
    ApiResponse<String> deleteTransactionDetail(@PathVariable("TDid") UUID id) {
        transactionDetailService.deleteTransactionDetail(id);
        return ApiResponse.<String>builder()
                .result("Transaction Detail have been deleted")
                .build();
    }
}
