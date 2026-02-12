package com.example.TransportHC.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.TransportHC.dto.request.SalaryReportRequest;
import com.example.TransportHC.dto.response.SalaryReportResponse;
import com.example.TransportHC.dto.response.SalaryReportSummaryResponse;
import com.example.TransportHC.dto.response.UserResponse;
import com.example.TransportHC.entity.Role;
import com.example.TransportHC.entity.SalaryReport;
import com.example.TransportHC.entity.User;
import com.example.TransportHC.enums.SalaryReportStatus;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.salaryreport.SalaryReportRepository;
import com.example.TransportHC.repository.user.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SalaryReportService {

    SalaryReportRepository salaryReportRepository;
    UserRepository userRepository;

    @PreAuthorize("hasAuthority('CREATE_1_SALARY_REPORT')")
    public SalaryReportResponse create1SalaryReport(Long userId, SalaryReportRequest request) {

        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);

        if (salaryReportRepository.existsByUser_UserIdAndMonth(userId, currentMonth)) {
            throw new AppException(ErrorCode.REPORT_EXISTED);
        }

        User driver = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        User createBy = userRepository
                .findUserByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        BigDecimal total = request.getBasicSalary()
                .add(request.getReward())
                .add(request.getCost())
                .subtract(request.getAdvanceMoney());

        SalaryReport salaryReport = SalaryReport.builder()
                .user(driver)
                .basicSalary(request.getBasicSalary())
                .reward(request.getReward())
                .cost(request.getCost())
                .advanceMoney(request.getAdvanceMoney())
                .total(total)
                .month(currentMonth)
                .createBy(createBy)
                .createAt(LocalDateTime.now())
                .status(SalaryReportStatus.PENDING)
                .build();

        salaryReportRepository.save(salaryReport);

        return entityToResponse(salaryReport);
    }

    @PreAuthorize("hasAuthority('CREATE_ALL_SALARY_REPORT')")
    public List<SalaryReportResponse> createAllSalaryReport() {

        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        User createBy = userRepository
                .findUserByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<User> users = userRepository.findAllNonAdminUsers();

        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);

        List<SalaryReport> reports = users.stream()
                .filter(user -> !salaryReportRepository.existsByUser_UserIdAndMonth(user.getUserId(), currentMonth))
                .map(user -> buildSalaryReport(user, createBy, currentMonth))
                .toList();

        salaryReportRepository.saveAll(reports);
        return reports.stream().map(this::entityToResponse).toList();
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('VIEW_SALARY_REPORT_DETAIL')")
    public SalaryReportResponse viewSalaryReportDetail(Long reportId) {

        SalaryReport report = salaryReportRepository
                .findById(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));

        return entityToResponse(report);
    }

    @PreAuthorize("hasAuthority('VIEW_SALARY_REPORT')")
    @Transactional(readOnly = true)
    public List<SalaryReportSummaryResponse> viewSalaryReportByMonth(LocalDate month) {
        LocalDate normalizedMonth = month.withDayOfMonth(1);
        return salaryReportRepository.findByMonth(normalizedMonth).stream()
                .map(this::entityToSummaryResponse)
                .toList();
    }

    @PreAuthorize("hasAuthority('UPDATE_SALARY_REPORT')")
    public SalaryReportResponse updateSalaryReport(Long reportId, SalaryReportRequest request) {

        SalaryReport report = salaryReportRepository
                .findById(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));

        if (report.getStatus() == SalaryReportStatus.DONE) {
            throw new AppException(ErrorCode.REPORT_ALREADY_DONE);
        }

        BigDecimal total = request.getBasicSalary()
                .add(request.getReward())
                .add(request.getCost())
                .subtract(request.getAdvanceMoney());

        report.setBasicSalary(request.getBasicSalary());
        report.setReward(request.getReward());
        report.setCost(request.getCost());
        report.setAdvanceMoney(request.getAdvanceMoney());
        report.setTotal(total);

        salaryReportRepository.save(report);

        return entityToResponse(report);
    }

    @PreAuthorize("hasAuthority('DELETE_SALARY_REPORT')")
    public void deleteSalaryReport(Long salaryReportId) {
        SalaryReport salaryReport = salaryReportRepository
                .findById(salaryReportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));

        salaryReportRepository.delete(salaryReport);
    }

    @PreAuthorize("hasAuthority('APPROVE_SALARY_REPORT')")
    public SalaryReportResponse checkSalaryReport(Long salaryReportId) {
        SalaryReport salaryReport = salaryReportRepository
                .findById(salaryReportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));

        if (salaryReport.getStatus() == SalaryReportStatus.DONE) {
            throw new AppException(ErrorCode.REPORT_ALREADY_DONE);
        }

        salaryReport.setStatus(SalaryReportStatus.DONE);

        User user = salaryReport.getUser();
        user.setAdvanceMoney(BigDecimal.ZERO);
        userRepository.save(user);
        salaryReportRepository.save(salaryReport);
        return entityToResponse(salaryReport);
    }

    private SalaryReport buildSalaryReport(User user, User createBy, LocalDate month) {

        LocalDate endDate = month.withDayOfMonth(month.lengthOfMonth());

        BigDecimal reward = salaryReportRepository.sumReward(user.getUserId(), month, endDate);

        BigDecimal cost = salaryReportRepository.sumCost(user.getUserId(), month, endDate);

        BigDecimal total = user.getBasicSalary().add(reward).add(cost).subtract(user.getAdvanceMoney());

        return SalaryReport.builder()
                .user(user)
                .basicSalary(user.getBasicSalary())
                .advanceMoney(user.getAdvanceMoney())
                .reward(reward)
                .cost(cost)
                .total(total)
                .month(month)
                .createBy(createBy)
                .createAt(LocalDateTime.now())
                .status(SalaryReportStatus.PENDING)
                .build();
    }

    private UserResponse mapUserToResponse(User user) {
        if (user == null) {
            return null;
        }
        Set<String> roles = user.getRoles() != null
                ? user.getRoles().stream().map(Role::getCode).collect(Collectors.toSet())
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

    private SalaryReportResponse entityToResponse(SalaryReport salaryReport) {
        UserResponse driver = mapUserToResponse(salaryReport.getUser());
        UserResponse createBy = mapUserToResponse(salaryReport.getCreateBy());

        return SalaryReportResponse.builder()
                .salaryReportId(salaryReport.getReportId())
                .user(driver)
                .basic_salary(salaryReport.getBasicSalary())
                .advance_salary(salaryReport.getAdvanceMoney())
                .reward(salaryReport.getReward())
                .cost(salaryReport.getCost())
                .total_salary(salaryReport.getTotal())
                .month(salaryReport.getMonth())
                .createBy(createBy)
                .createdAt(salaryReport.getCreateAt())
                .status(salaryReport.getStatus())
                .build();
    }

    private SalaryReportSummaryResponse entityToSummaryResponse(SalaryReport report) {

        return SalaryReportSummaryResponse.builder()
                .reportId(report.getReportId())
                .user(mapUserToResponse(report.getUser()))
                .baseSalary(report.getBasicSalary())
                .advanceSalary(report.getAdvanceMoney())
                .rewardSalary(report.getReward())
                .costSalary(report.getCost())
                .total(report.getTotal())
                .month(report.getMonth())
                .status(report.getStatus())
                .build();
    }
}
