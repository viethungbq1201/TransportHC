package com.example.TransportHC.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.TransportHC.dto.request.InventoryCreateRequest;
import com.example.TransportHC.dto.request.InventoryFilterRequest;
import com.example.TransportHC.dto.request.InventoryUpdateRequest;
import com.example.TransportHC.dto.response.ApiResponse;
import com.example.TransportHC.dto.response.InventoryResponse;
import com.example.TransportHC.service.InventoryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

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

    @GetMapping("/findInventory/{inventoryId}")
    ApiResponse<InventoryResponse> findInventoryById(@PathVariable("inventoryId") UUID id) {
        return ApiResponse.<InventoryResponse>builder()
                .result(inventoryService.findInventoryById(id))
                .build();
    }

    @PutMapping("/updateInventory/{inventoryId}")
    ApiResponse<InventoryResponse> updateInventory(
            @PathVariable("inventoryId") UUID id, @RequestBody InventoryUpdateRequest request) {
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

    @PostMapping("/filterInventory")
    ApiResponse<List<InventoryResponse>> filterInventory(@RequestBody InventoryFilterRequest request) {
        return ApiResponse.<List<InventoryResponse>>builder()
                .result(inventoryService.filterInventory(request))
                .build();
    }

    @GetMapping("/exportInventory")
    public ResponseEntity<byte[]> exportExcel() {

        byte[] data = inventoryService.exportInventoryToExcel();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=inventory.xlsx")
                .contentType(
                        MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
    }

    @PostMapping(value = "/importInventory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> importInventory(@RequestParam("file") MultipartFile file) {
        inventoryService.importInventoryFromExcel(file);
        return ApiResponse.<String>builder()
                .result("Import inventory successfully")
                .build();
    }
}
