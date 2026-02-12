package com.example.TransportHC.service;

import java.util.List;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.TransportHC.dto.request.CostTypeCreateRequest;
import com.example.TransportHC.entity.CostType;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.CostTypeRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CostTypeService {

    CostTypeRepository costTypeRepository;

    @PreAuthorize("hasAuthority('CREATE_COST_TYPE')")
    public CostType createCostType(CostTypeCreateRequest request) {
        CostType costType = CostType.builder().name(request.getName()).build();
        costTypeRepository.save(costType);
        return costType;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('VIEW_COST_TYPE')")
    public List<CostType> viewCostType() {
        return costTypeRepository.findAll().stream().toList();
    }

    @PreAuthorize("hasAuthority('UPDATE_COST_TYPE')")
    public CostType updateCostType(Long id, CostTypeCreateRequest request) {
        CostType costType =
                costTypeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COST_TYPE_NOT_FOUND));

        costType.setName(request.getName());
        costTypeRepository.save(costType);
        return costType;
    }

    @PreAuthorize("hasAuthority('DELETE_COST_TYPE')")
    public void deleteCostType(Long id) {
        CostType costType =
                costTypeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COST_TYPE_NOT_FOUND));
        costTypeRepository.delete(costType);
    }
}
