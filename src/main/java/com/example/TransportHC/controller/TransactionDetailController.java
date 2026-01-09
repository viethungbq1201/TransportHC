package com.example.TransportHC.controller;


import com.example.TransportHC.dto.request.TransactionCreateRequest;
import com.example.TransportHC.dto.request.TransactionDetailCreateRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.dto.response.TransactionDetailResponse;
import com.example.TransportHC.dto.response.TransactionResponse;
import com.example.TransportHC.entity.TransactionDetail;
import com.example.TransportHC.service.TransactionDetailService;
import com.example.TransportHC.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/transactiondetail")
@RestController
public class TransactionDetailController {

    TransactionDetailService transactionDetailService;

    @PostMapping("/createTransactionDetail")
    ApiResponse<TransactionDetailResponse> createTransaction(@RequestBody TransactionDetailCreateRequest request) {
        return ApiResponse.<TransactionDetailResponse>builder()
                .result(transactionDetailService.createTransactionResponse(request))
                .build();
    }

    @GetMapping("/viewTransactionDetail")
    ApiResponse<List<TransactionDetailResponse>> viewTransactions() {
        return ApiResponse.<List<TransactionDetailResponse>>builder()
                .result(transactionDetailService.viewTransactionDetailResponse())
                .build();
    }

    @DeleteMapping("/deleteTransactionDetail/{TDid}")
    String deleteTransactionDetail(@PathVariable("TDid") UUID id) {
        transactionDetailService.deleteTransactionDetail(id);
        return "Transaction Detail have been deleted";
    }
}
