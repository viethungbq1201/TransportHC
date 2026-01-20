package com.example.TransportHC.service;

import com.example.TransportHC.dto.request.ScheduleCreateRequest;
import com.example.TransportHC.dto.request.ScheduleEndRequest;
import com.example.TransportHC.dto.request.ScheduleUpdateRequest;
import com.example.TransportHC.dto.response.*;
import com.example.TransportHC.entity.*;
import com.example.TransportHC.enums.ApproveStatus;
import com.example.TransportHC.enums.TruckStatus;
import com.example.TransportHC.enums.UserStatus;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleService {

    ScheduleRepository scheduleRepository;
    UserRepository userRepository;
    TruckRepository truckRepository;
    RouteRepository routeRepository;
    TransactionRepository transactionRepository;

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public ScheduleResponse createSchedule(ScheduleCreateRequest request) {
        User driver = userRepository.findById(request.getDriverId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Truck truck = truckRepository.findById(request.getTruckId())
                .orElseThrow(() -> new AppException(ErrorCode.TRUCK_NOT_FOUND));

        Route route = routeRepository.findById(request.getRouteId())
                .orElseThrow(() -> new AppException(ErrorCode.ROUTE_NOT_FOUND));

        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        if (driver.getStatus() != UserStatus.AVAILABLE) {
            throw new AppException(ErrorCode.USER_NOT_AVAILABLE);
        }
        if (truck.getStatus() != TruckStatus.AVAILABLE) {
            throw new AppException(ErrorCode.TRUCK_NOT_AVAILABLE);
        }

        Schedule schedule = Schedule.builder()
                .startDate(request.getStartDate())
                .endDate(null)
                .documentaryProof("")
                .approveStatus(ApproveStatus.PENDING)
                .reward(request.getReward())
                .driver(driver)
                .truck(truck)
                .route(route)
                .transaction(transaction)
                .build();

        scheduleRepository.save(schedule);
        return entityToResponse(schedule);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('CREATE_COST')")
    public List<ScheduleResponse> viewSchedule() {
        return scheduleRepository.findAll().stream()
                .map(this::entityToResponse)
                .toList();
    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public ScheduleResponse updateSchedule(UUID id, ScheduleUpdateRequest request) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));

        if (schedule.getApproveStatus() == ApproveStatus.APPROVED) {
            throw new AppException(ErrorCode.SCHEDULE_ALREADY_APPROVED);
        }

        User driver = userRepository.findById(request.getDriverId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Truck truck = truckRepository.findById(request.getTruckId())
                .orElseThrow(() -> new AppException(ErrorCode.TRUCK_NOT_FOUND));

        Route route = routeRepository.findById(request.getRouteId())
                .orElseThrow(() -> new AppException(ErrorCode.ROUTE_NOT_FOUND));

        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        if (driver.getStatus() != UserStatus.AVAILABLE) {
            throw new AppException(ErrorCode.USER_NOT_AVAILABLE);
        }
        if (!driver.getIsDriver()) {
            throw new AppException(ErrorCode.USER_IS_NOT_DRIVER);
        }
        if (truck.getStatus() != TruckStatus.AVAILABLE) {
            throw new AppException(ErrorCode.TRUCK_NOT_AVAILABLE);
        }
        if (request.getEndDate() != null &&
                request.getEndDate().isBefore(request.getStartDate())) {
            throw new AppException(ErrorCode.INVALID_SCHEDULE_DATE);
        }

        schedule.setStartDate(request.getStartDate());
        schedule.setEndDate(request.getEndDate());
        schedule.setReward(request.getReward());
        schedule.setDriver(driver);
        schedule.setTruck(truck);
        schedule.setRoute(route);
        schedule.setTransaction(transaction);
        scheduleRepository.save(schedule);

        return entityToResponse(schedule);
    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public ScheduleResponse approveSchedule(UUID id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));

        if (schedule.getApproveStatus() != ApproveStatus.PENDING) {
            throw new AppException(ErrorCode.SCHEDULE_ALREADY_APPROVED);
        }

        var context =  SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User approve = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        schedule.setApprovedBy(approve);
        schedule.setApproveStatus(ApproveStatus.APPROVED);
        scheduleRepository.save(schedule);
        checkStatus(schedule);
        return entityToResponse(schedule);

    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public ScheduleResponse rejectSchedule(UUID id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));

        if (schedule.getApproveStatus() != ApproveStatus.PENDING) {
            throw new AppException(ErrorCode.SCHEDULE_ALREADY_APPROVED);
        }

        schedule.setApproveStatus(ApproveStatus.REJECTED);
        scheduleRepository.save(schedule);
        return entityToResponse(schedule);

    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public ScheduleResponse endSchedule(UUID id, ScheduleEndRequest request) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));

        if (schedule.getApproveStatus() == ApproveStatus.PENDING) {
            throw new AppException(ErrorCode.SCHEDULE_IS_PENDING);
        }

        schedule.setEndDate(LocalDate.now());
        schedule.setDocumentaryProof(request.getDocumentaryProof());
        scheduleRepository.save(schedule);

        return entityToResponse(schedule);

    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public void deleteSchedule(UUID id) {
        Schedule schedule = scheduleRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));

        if (schedule.getApproveStatus() == ApproveStatus.APPROVED) {
            schedule.getDriver().setStatus(UserStatus.AVAILABLE);
            schedule.getTruck().setStatus(TruckStatus.AVAILABLE);
        }

        scheduleRepository.delete(schedule);
    }

    private void checkStatus(Schedule schedule) {
        if (schedule.getApproveStatus() == ApproveStatus.APPROVED) {
            schedule.getDriver().setStatus(UserStatus.BUSY);
            schedule.getTruck().setStatus(TruckStatus.IN_USE);
        }
        scheduleRepository.save(schedule);
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

    private ScheduleResponse entityToResponse(Schedule schedule) {

        UserResponse approveResponse = mapUserToResponse(schedule.getApprovedBy());
        UserResponse driverResponse = mapUserToResponse(schedule.getDriver());

        TruckResponse truckResponse = TruckResponse.builder()
                .id(schedule.getTruck().getTruckId())
                .licensePlate(schedule.getTruck().getLicensePlate())
                .capacity(schedule.getTruck().getCapacity())
                .status(schedule.getTruck().getStatus())
                .build();

        RouteResponse routeResponse = RouteResponse.builder()
                .id(schedule.getRoute().getRouteId())
                .name(schedule.getRoute().getName())
                .start_point(schedule.getRoute().getStart_point())
                .end_point(schedule.getRoute().getEnd_point())
                .distance(schedule.getRoute().getDistance())
                .build();

        TransactionResponse transactionResponse = TransactionResponse.builder()
                .transactionId(schedule.getTransaction().getTransactionId())
                .transactionType(schedule.getTransaction().getType())
                .date(schedule.getTransaction().getDate())
                .location(schedule.getTransaction().getLocation())
                .note(schedule.getTransaction().getNote())
                .createdBy(driverResponse)
                .build();

        return ScheduleResponse.builder()
                .scheduleId(schedule.getSchedulesId())
                .startDate(schedule.getStartDate())
                .endDate(schedule.getEndDate())
                .documentaryProof(schedule.getDocumentaryProof())
                .approveStatus(schedule.getApproveStatus())
                .reward(schedule.getReward())
                .approveBy(approveResponse)
                .driver(driverResponse)
                .truck(truckResponse)
                .route(routeResponse)
                .transaction(transactionResponse)
                .build();
    }
}
