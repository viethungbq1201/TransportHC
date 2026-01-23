package com.example.TransportHC.repository.salaryreport;

import static com.example.TransportHC.entity.QCost.cost;
import static com.example.TransportHC.entity.QSchedule.schedule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.example.TransportHC.enums.ApproveStatus;
import com.example.TransportHC.enums.ScheduleStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SalaryReportRepositoryCustomImpl implements SalaryReportRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void init() {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public BigDecimal sumReward(UUID userId, LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .select(schedule.reward.sum().coalesce(BigDecimal.ZERO))
                .from(schedule)
                .where(
                        schedule.driver.userId.eq(userId),
                        schedule.approveStatus.eq(ScheduleStatus.valueOf("DONE")),
                        schedule.endDate.between(startDate, endDate))
                .fetchOne();
    }

    @Override
    public BigDecimal sumCost(UUID userId, LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .select(cost.price.sum().coalesce(BigDecimal.ZERO))
                .from(cost)
                .where(
                        cost.userCost.userId.eq(userId),
                        cost.approveStatus.eq(ApproveStatus.valueOf("APPROVED")),
                        cost.date.between(startDate, endDate))
                .fetchOne();
    }
}
