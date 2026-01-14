package com.example.TransportHC.controller;

import com.example.TransportHC.dto.request.CostTypeCreateRequest;
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
    CostType createCostType(@RequestBody CostTypeCreateRequest request) {
        return costTypeService.createCostType(request);
    }

    @GetMapping("/viewCostType")
    List<CostType> viewCostType() {
        return costTypeService.viewCostType();
    }

    @PutMapping("/updateCostType/{costTypeId}")
    CostType updateCostType(@PathVariable("costTypeId") UUID id, @RequestBody CostTypeCreateRequest request) {
        return costTypeService.updateCostType(id, request);
    }

    @DeleteMapping("/delelteCostType/{costTypeId}")
    String deleteCostType(@PathVariable("costTypeId") UUID id) {
        costTypeService.deleteCostType(id);
        return "Cost Type have been delete";
    }
}
