package com.example.TransportHC.service;

import com.example.TransportHC.repository.SalaryReportRepository;
import com.example.TransportHC.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SalaryReportService {

    SalaryReportRepository salaryReportRepository;
    UserRepository userRepository;

//    public SalaryReportResponse create1SalaryReport(UUID userId)  {
//        User driver = userRepository.findById(userId)
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
//
//        SalaryReport salaryReport = SalaryReport.builder()
//                .basicSalary(driver.getBasicSalary())
//                .
//                .cost()
//                .createBy()
//                .build()
//    }
//
//
//
//    private SalaryReportResponse entityToResponse(User user) {
//        return SalaryReportResponse.builder()
//                .basic_salary(user.getBasicSalary())
//                .cost()
//                .build();
//    }
}
