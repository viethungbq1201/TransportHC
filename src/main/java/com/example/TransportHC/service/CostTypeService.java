package com.example.TransportHC.service;

import com.example.TransportHC.dto.request.CostTypeCreateRequest;
import com.example.TransportHC.entity.CostType;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.CostTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CostTypeService {

    CostTypeRepository costTypeRepository;

    public CostType createCostType (CostTypeCreateRequest request) {
        CostType costType = CostType.builder()
                .name(request.getName())
                .build();
        costTypeRepository.save(costType);
        return costType;
    }

    @Transactional(readOnly = true)
    public List<CostType> viewCostType() {
        return costTypeRepository.findAll().stream().toList();
    }

    public CostType updateCostType(UUID id, CostTypeCreateRequest request) {
        CostType costType = costTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COST_TYPE_NOT_FOUND));

        costType.setName(request.getName());
        costTypeRepository.save(costType);
        return costType;
    }

    public void deleteCostType(UUID id) {
        CostType costType = costTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COST_TYPE_NOT_FOUND));
        costTypeRepository.delete(costType);
    }

}
