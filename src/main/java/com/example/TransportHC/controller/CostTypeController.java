package com.example.TransportHC.controller;

import com.example.TransportHC.dto.request.CostTypeCreateRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.entity.CostType;
import com.example.TransportHC.service.CostTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/costType")
@RestController
public class CostTypeController {

    CostTypeService costTypeService;

    @PostMapping("/createCostType")
    ApiResponse<CostType> createCostType(@RequestBody CostTypeCreateRequest request) {
        return ApiResponse.<CostType>builder()
                .result(costTypeService.createCostType(request))
                .build();
    }

    @GetMapping("/viewCostType")
    ApiResponse<List<CostType>> viewCostType() {
        return ApiResponse.<List<CostType>>builder()
                .result(costTypeService.viewCostType())
                .build();
    }

    @PutMapping("/updateCostType/{costTypeId}")
    ApiResponse<CostType> updateCostType(@PathVariable("costTypeId") UUID id, @RequestBody CostTypeCreateRequest request) {
        return ApiResponse.<CostType>builder()
                .result(costTypeService.updateCostType(id, request))
                .build();
    }

    @DeleteMapping("/delelteCostType/{costTypeId}")
    ApiResponse<String> deleteCostType(@PathVariable("costTypeId") UUID id) {
        costTypeService.deleteCostType(id);
        return ApiResponse.<String>builder()
                .result("Cost Type have been delete")
                .build();
    }
}
