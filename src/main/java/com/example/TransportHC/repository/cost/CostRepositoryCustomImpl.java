package com.example.TransportHC.repository.cost;

import static com.example.TransportHC.entity.QCost.cost;
import static com.example.TransportHC.entity.QSchedule.schedule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.TransportHC.entity.QCost;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.example.TransportHC.entity.Cost;
import com.example.TransportHC.enums.ApproveStatus;
import com.example.TransportHC.enums.ScheduleStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CostRepositoryCustomImpl implements CostRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void init() {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Cost> findCostsByTruck(UUID truckId, LocalDate from, LocalDate to) {
        return queryFactory
                .selectFrom(cost)
                .join(cost.schedule, schedule).fetchJoin()
                .where(
                        schedule.truck.truckId.eq(truckId),
                        cost.date.between(from, to),
                        cost.approveStatus.eq(ApproveStatus.valueOf("APPROVED")),
                        schedule.approveStatus.eq(ScheduleStatus.valueOf("DONE")))
                .fetch();
    }

    @Override
    public BigDecimal sumCostByTruck(UUID truckId, LocalDate from, LocalDate to) {
        return queryFactory
                .select(cost.price.sum().coalesce(BigDecimal.ZERO))
                .from(cost)
                .join(cost.schedule, schedule)
                .where(
                        schedule.truck.truckId.eq(truckId),
                        cost.date.between(from, to),
                        cost.approveStatus.eq(ApproveStatus.valueOf("APPROVED")),
                        schedule.approveStatus.eq(ScheduleStatus.valueOf("DONE")))
                .fetchOne();
    }

    @Override
    public List<Object[]> sumCostAllTrucks(LocalDate from, LocalDate to) {
        return queryFactory
                .select(
                        schedule.truck.truckId,
                        schedule.truck.licensePlate,
                        cost.price.sum().coalesce(BigDecimal.ZERO)
                )
                .from(cost)
                .join(cost.schedule, schedule)
                .where(
                        cost.date.between(from, to),
                        cost.approveStatus.eq(ApproveStatus.APPROVED),
                        schedule.approveStatus.eq(ScheduleStatus.DONE)
                )
                .groupBy(schedule.truck.truckId, schedule.truck.licensePlate)
                .fetch()
                .stream()
                .map(t -> new Object[] {
                        t.get(0, UUID.class),
                        t.get(1, String.class),
                        t.get(2, BigDecimal.class)
                })
                .toList();
    }


    @Override
    public BigDecimal sumAllCosts(LocalDate from, LocalDate to) {
        return queryFactory
                .select(cost.price.sum().coalesce(BigDecimal.ZERO))
                .from(cost)
                .where(cost.date.between(from, to), cost.approveStatus.eq(ApproveStatus.valueOf("APPROVED")))
                .fetchOne();
    }

    @Override
    public Map<UUID, BigDecimal> sumCostBySchedule(List<UUID> scheduleIds) {

        QCost c = QCost.cost;

        return queryFactory
                .select(c.schedule.schedulesId, c.price.sum())
                .from(c)
                .where(c.schedule.schedulesId.in(scheduleIds))
                .groupBy(c.schedule.schedulesId)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        t -> t.get(0, UUID.class),
                        t -> t.get(1, BigDecimal.class)
                ));
    }


    @Override
    public BigDecimal sumCostByDriver(UUID driverId, LocalDate from, LocalDate to) {
        return queryFactory
                .select(cost.price.sum().coalesce(BigDecimal.ZERO))
                .from(cost)
                .where(
                        cost.userCost.userId.eq(driverId),
                        cost.date.between(from, to),
                        cost.approveStatus.eq(ApproveStatus.valueOf("APPROVED")))
                .fetchOne();
    }
}
