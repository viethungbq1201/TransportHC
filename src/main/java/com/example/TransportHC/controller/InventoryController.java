package com.example.TransportHC.controller;


import com.example.TransportHC.dto.request.InventoryCreateRequest;
import com.example.TransportHC.dto.request.InventoryUpdateRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.dto.response.InventoryResponse;
import com.example.TransportHC.entity.Inventory;
import com.example.TransportHC.repository.InventoryRepository;
import com.example.TransportHC.service.InventoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/inventory")
@RestController
public class InventoryController {

    InventoryService inventoryService;

    @PostMapping("/createInventory")
    ApiResponse<InventoryResponse> createInventory(@RequestBody InventoryCreateRequest request) {
        return ApiResponse.<InventoryResponse>builder()
                .result(inventoryService.createInventory(request))
                .build();
    }

    @GetMapping("/viewInventory")
    ApiResponse<List<InventoryResponse>> viewInventory() {
        return ApiResponse.<List<InventoryResponse>>builder()
                .result(inventoryService.viewInventory())
                .build();
    }

    @PutMapping("/updateInventory/{inventoryId}")
    ApiResponse<InventoryResponse> updateInventory(@PathVariable("inventoryId") UUID id, @RequestBody InventoryUpdateRequest request) {
        return ApiResponse.<InventoryResponse>builder()
                .result(inventoryService.updateInventory(id, request))
                .build();
    }

    @DeleteMapping("/deleteInventory/{inventoryId}")
    ApiResponse<String> deleteInventory(@PathVariable("inventoryId") UUID id) {
        inventoryService.deleteInventory(id);
        return ApiResponse.<String>builder()
                .result("Inventory have been deleted")
                .build();
    }

}
