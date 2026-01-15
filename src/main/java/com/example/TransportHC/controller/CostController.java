package com.example.TransportHC.controller;

import com.example.TransportHC.dto.request.CostCreateRequest;
import com.example.TransportHC.dto.request.CostTypeCreateRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.dto.response.CostResponse;
import com.example.TransportHC.entity.CostType;
import com.example.TransportHC.service.CostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/cost")
@RestController
public class CostController {

    CostService costService;

    @PostMapping("/createCost")
    ApiResponse<CostResponse> createCost(@RequestBody CostCreateRequest request) {
        return ApiResponse.<CostResponse>builder()
                .result(costService.createCost(request))
                .build();

    }

    @GetMapping("/viewCost")
    ApiResponse<List<CostResponse>> viewCost() {
        return ApiResponse.<List<CostResponse>>builder()
                .result(costService.viewCost())
                .build();
    }

    @PutMapping("/updateCost/{costId}")
    ApiResponse<CostResponse> updateCost(@PathVariable("costId") UUID id, @RequestBody CostCreateRequest request) {
        return ApiResponse.<CostResponse>builder()
                .result(costService.updateCost(id, request))
                .build();
    }

    @GetMapping("/approveCost/{costId}")
    ApiResponse<CostResponse> approveCost(@PathVariable("costId") UUID id) {
        return ApiResponse.<CostResponse>builder()
                .result(costService.approveStatus(id))
                .build();
    }

    @GetMapping("/rejectCost/{costId}")
    ApiResponse<CostResponse> rejectCost(@PathVariable("costId") UUID id) {
        return ApiResponse.<CostResponse>builder()
                .result(costService.rejectStatus(id))
                .build();
    }

    @DeleteMapping("/delelteCost/{costId}")
    ApiResponse<String> deleteCost(@PathVariable("costId") UUID id) {
        costService.deleteCost(id);
        return ApiResponse.<String>builder()
                .result("Cost have been deleted")
                .build();
    }
}
