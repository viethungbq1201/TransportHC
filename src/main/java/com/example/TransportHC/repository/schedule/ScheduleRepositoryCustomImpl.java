package com.example.TransportHC.repository.schedule;

import static com.example.TransportHC.entity.QCost.cost;
import static com.example.TransportHC.entity.QSchedule.schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.example.TransportHC.entity.Schedule;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void init() {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Schedule> findSchedulesByTruck(UUID truckId, LocalDate from, LocalDate to) {
        return queryFactory
                .selectFrom(schedule)
                .where(schedule.truck.truckId.eq(truckId), schedule.endDate.between(from, to))
                .fetch();
    }

    @Override
    public List<Object[]> summarySchedulesAllTrucks(LocalDate from, LocalDate to) {
        return queryFactory
                .select(schedule.truck.truckId, schedule.schedulesId.count(), schedule.reward.sum(), cost.price.sum())
                .from(schedule)
                .leftJoin(cost)
                .on(cost.schedule.schedulesId.eq(schedule.schedulesId))
                .where(schedule.endDate.between(from, to))
                .groupBy(schedule.truck.truckId)
                .fetch()
                .stream()
                .map(t -> new Object[] {
                    t.get(0, UUID.class),
                    t.get(1, Long.class),
                    t.get(2, java.math.BigDecimal.class),
                    t.get(3, java.math.BigDecimal.class)
                })
                .toList();
    }

    @Override
    public List<Object[]> countTripsByTruck(LocalDate from, LocalDate to) {
        return queryFactory
                .select(schedule.truck.truckId, schedule.schedulesId.count())
                .from(schedule)
                .where(schedule.endDate.between(from, to))
                .groupBy(schedule.truck.truckId)
                .fetch()
                .stream()
                .map(t -> new Object[] {t.get(0, UUID.class), t.get(1, Long.class)})
                .toList();
    }
}
