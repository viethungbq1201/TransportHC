package com.example.TransportHC.service;

import com.example.TransportHC.dto.request.TruckCreateRequest;
import com.example.TransportHC.dto.response.TruckResponse;
import com.example.TransportHC.entity.Truck;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.TruckRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TruckService {
    TruckRepository truckRepository;

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public TruckResponse createTruck (TruckCreateRequest request) {

        if (truckRepository.existsTrucksByLicensePlate(request.getLicensePlate())) {
            throw new AppException(ErrorCode.TRUCK_EXISTED);
        }

        Truck truck = Truck.builder()
                .licensePlate(request.getLicensePlate())
                .capacity(request.getCapacity())
                .status(request.getStatus())
                .build();
        truckRepository.save(truck);
        return entityToDto(truck);
    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public List<TruckResponse> viewTruck () {
        return truckRepository.findAll().stream()
                .map(this::entityToDto)
                .toList();
    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public TruckResponse updateTruck (UUID id, TruckCreateRequest request) {
        Truck truck = truckRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TRUCK_NOT_FOUND));

        truck.setLicensePlate(request.getLicensePlate());
        truck.setCapacity(request.getCapacity());
        truck.setStatus(request.getStatus());
        truckRepository.save(truck);
        return entityToDto(truck);
    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public void deleteTruck (UUID id) {
        Truck truck = truckRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TRUCK_NOT_FOUND));
        truckRepository.delete(truck);
    }

    TruckResponse entityToDto (Truck truck) {
        return TruckResponse.builder()
                .id(truck.getTruckId())
                .licensePlate(truck.getLicensePlate())
                .capacity(truck.getCapacity())
                .status(truck.getStatus())
                .build();
    }

}
