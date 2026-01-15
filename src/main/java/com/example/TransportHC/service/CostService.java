package com.example.TransportHC.service;

import com.example.TransportHC.dto.request.CostCreateRequest;
import com.example.TransportHC.dto.response.*;
import com.example.TransportHC.entity.*;
import com.example.TransportHC.enums.ApproveStatus;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.CostRepository;
import com.example.TransportHC.repository.CostTypeRepository;
import com.example.TransportHC.repository.ScheduleRepository;
import com.example.TransportHC.repository.UserRepository;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CostService {
    CostRepository costRepository;
    CostTypeRepository costTypeRepository;
    ScheduleRepository scheduleRepository;
    UserRepository userRepository;

    public CostResponse createCost(CostCreateRequest request) {

        CostType costType = costTypeRepository.findById(request.getCostTypeId())
                .orElseThrow(() -> new AppException(ErrorCode.COST_TYPE_NOT_FOUND));

        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));

        if (schedule.getApproveStatus() == ApproveStatus.PENDING) {
            throw new AppException(ErrorCode.SCHEDULE_IS_PENDING);
        }

        Cost cost = Cost.builder()
                .description(request.getDescription())
                .price(request.getPrice())
                .date(LocalDate.now())
                .approveStatus(ApproveStatus.PENDING)
                .costType(costType)
                .schedule(schedule)
                .approvedBy(null)
                .build();
        costRepository.save(cost);

        return entityToResponse(cost);

    }

    public List<CostResponse> viewCost() {
        return costRepository.findAll().stream()
                .map(this::entityToResponse)
                .toList();
    }

    public CostResponse updateCost(UUID id, CostCreateRequest request) {
        Cost cost = costRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COST_NOT_FOUND));

        CostType costType = costTypeRepository.findById(request.getCostTypeId())
                .orElseThrow(() -> new AppException(ErrorCode.COST_TYPE_NOT_FOUND));

        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));


        cost.setDescription(request.getDescription());
        cost.setPrice(request.getPrice());
        cost.setCostType(costType);
        cost.setSchedule(schedule);

        costRepository.save(cost);
        return entityToResponse(cost);
    }

    public CostResponse approveStatus(UUID id) {
        Cost cost = costRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COST_NOT_FOUND));

        if (cost.getApproveStatus() != ApproveStatus.PENDING) {
            throw new AppException(ErrorCode.SCHEDULE_ALREADY_APPROVED);
        }

        cost.setApproveStatus(ApproveStatus.APPROVED);
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        cost.setApprovedBy(user);
        costRepository.save(cost);
        return entityToResponse(cost);
    }

    public CostResponse rejectStatus(UUID id) {
        Cost cost = costRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COST_NOT_FOUND));

        if (cost.getApproveStatus() != ApproveStatus.PENDING) {
            throw new AppException(ErrorCode.SCHEDULE_ALREADY_APPROVED);
        }

        cost.setApproveStatus(ApproveStatus.REJECTED);
        costRepository.save(cost);
        return entityToResponse(cost);
    }

    public void deleteCost(UUID id) {
        Cost cost = costRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COST_NOT_FOUND));

        costRepository.delete(cost);
    }


    private UserResponse mapUserToResponse(User user) {
        if (user == null) {
            return null;
        }
        Set<String> roles = user.getRoles() != null
                ? user.getRoles().stream()
                .map(Role::getCode)
                .collect(Collectors.toSet())
                : new HashSet<>();

        return UserResponse.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .address(user.getAddress())
                .phoneNumber(String.valueOf(user.getPhoneNumber()))
                .status(user.getStatus())
                .basicSalary(user.getBasicSalary())
                .advanceMoney(user.getAdvanceMoney())
                .roles(roles)
                .build();
    }

    private CostResponse entityToResponse (Cost cost){
        UserResponse approveResponse = mapUserToResponse(cost.getSchedule().getApprovedBy());
        UserResponse driverResponse = mapUserToResponse(cost.getSchedule().getDriver());
        UserResponse approveCostResponse = mapUserToResponse(cost.getApprovedBy());

        TruckResponse truckResponse = TruckResponse.builder()
                .id(cost.getSchedule().getTruck().getTruckId())
                .licensePlate(cost.getSchedule().getTruck().getLicensePlate())
                .capacity(cost.getSchedule().getTruck().getCapacity())
                .status(cost.getSchedule().getTruck().getStatus())
                .build();

        RouteResponse routeResponse = RouteResponse.builder()
                .id(cost.getSchedule().getRoute().getRouteId())
                .name(cost.getSchedule().getRoute().getName())
                .start_point(cost.getSchedule().getRoute().getStart_point())
                .end_point(cost.getSchedule().getRoute().getEnd_point())
                .distance(cost.getSchedule().getRoute().getDistance())
                .build();

        TransactionResponse transactionResponse = TransactionResponse.builder()
                .transactionId(cost.getSchedule().getTransaction().getTransactionId())
                .transactionType(cost.getSchedule().getTransaction().getType())
                .date(cost.getSchedule().getTransaction().getDate())
                .location(cost.getSchedule().getTransaction().getLocation())
                .note(cost.getSchedule().getTransaction().getNote())
                .createdBy(driverResponse)
                .build();

        ScheduleResponse schedule = ScheduleResponse.builder()
                .scheduleId(cost.getSchedule().getSchedulesId())
                .startDate(cost.getSchedule().getStartDate())
                .endDate(cost.getSchedule().getEndDate())
                .documentaryProof(cost.getSchedule().getDocumentaryProof())
                .approveStatus(cost.getSchedule().getApproveStatus())
                .reward(cost.getSchedule().getReward())
                .approveBy(approveResponse)
                .driver(driverResponse)
                .truck(truckResponse)
                .route(routeResponse)
                .transaction(transactionResponse)
                .build();

        return CostResponse.builder()
                .costId(cost.getCostId())
                .description(cost.getDescription())
                .price(cost.getPrice())
                .date(cost.getDate())
                .approveStatus(cost.getApproveStatus())
                .costType(cost.getCostType())
                .schedule(schedule)
                .approvedBy(approveCostResponse)
                .build();
    }
}
