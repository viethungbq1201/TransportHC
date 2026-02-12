package com.example.TransportHC.repository.schedule;

import static com.example.TransportHC.entity.QCost.cost;
import static com.example.TransportHC.entity.QSchedule.schedule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.example.TransportHC.dto.response.report.ScheduleWithCostDto;
import com.example.TransportHC.dto.response.report.TruckScheduleReportRow;
import com.example.TransportHC.entity.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

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
        public List<Schedule> findSchedulesByTruck(Long truckId, LocalDate from, LocalDate to) {
                return queryFactory
                                .selectFrom(schedule)
                                .where(schedule.truck.truckId.eq(truckId), schedule.endDate.between(from, to))
                                .fetch();
        }

        @Override
        public List<Object[]> summarySchedulesAllTrucks(LocalDate from, LocalDate to) {
                return queryFactory
                                .select(
                                                schedule.truck.truckId,
                                                schedule.truck.licensePlate,
                                                schedule.schedulesId.count(),
                                                schedule.reward.sum(),
                                                cost.price.sum().coalesce(BigDecimal.ZERO) // 4
                                )
                                .from(schedule)
                                .leftJoin(cost)
                                .on(cost.schedule.schedulesId.eq(schedule.schedulesId))
                                .where(schedule.endDate.between(from, to))
                                .groupBy(
                                                schedule.truck.truckId,
                                                schedule.truck.licensePlate)
                                .fetch()
                                .stream()
                                .map(t -> new Object[] {
                                                t.get(0, Long.class),
                                                t.get(1, String.class),
                                                t.get(2, Long.class),
                                                t.get(3, BigDecimal.class),
                                                t.get(4, BigDecimal.class)
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
                                .map(t -> new Object[] { t.get(0, Long.class), t.get(1, Long.class) })
                                .toList();
        }

        @Override
        public List<Schedule> findSchedulesByTruckAndDate(
                        Long truckId, LocalDate from, LocalDate to) {

                QSchedule s = QSchedule.schedule;
                QRoute r = QRoute.route;
                QUser u = QUser.user;

                return queryFactory
                                .selectFrom(s)
                                .join(s.route, r).fetchJoin()
                                .join(s.driver, u).fetchJoin()
                                .where(
                                                s.truck.truckId.eq(truckId),
                                                s.startDate.between(from, to))
                                .fetch();
        }

        @Override
        public List<Schedule> findSchedulesByDate(
                        LocalDate from,
                        LocalDate to) {

                QSchedule s = QSchedule.schedule;

                return queryFactory
                                .selectFrom(s)
                                .leftJoin(s.truck).fetchJoin()
                                .leftJoin(s.route).fetchJoin()
                                .leftJoin(s.driver).fetchJoin()
                                .where(
                                                s.startDate.goe(from),
                                                s.endDate.loe(to))
                                .fetch();
        }

        @Override
        public List<ScheduleWithCostDto> findSchedulesWithCost(
                        Long truckId,
                        LocalDate from,
                        LocalDate to) {

                QSchedule s = QSchedule.schedule;
                QTruck t = QTruck.truck;
                QRoute r = QRoute.route;
                QUser u = QUser.user;
                QCost c = QCost.cost;

                return queryFactory
                                .select(Projections.constructor(
                                                ScheduleWithCostDto.class,
                                                s.schedulesId,
                                                t.truckId,
                                                t.licensePlate,
                                                r.name,
                                                u.username,
                                                s.startDate,
                                                s.endDate,
                                                s.reward,
                                                c.price.sum().coalesce(BigDecimal.ZERO)))
                                .from(s)
                                .join(s.truck, t)
                                .join(s.route, r)
                                .join(s.driver, u)
                                .leftJoin(c).on(c.schedule.eq(s))
                                .where(
                                                s.truck.truckId.eq(truckId),
                                                s.startDate.between(from, to))
                                .groupBy(s.schedulesId)
                                .fetch();
        }

        @Override
        public List<ScheduleWithCostDto> findSchedulesWithCostForAllTrucks(
                        LocalDate from,
                        LocalDate to) {

                QSchedule s = QSchedule.schedule;
                QTruck t = QTruck.truck;
                QRoute r = QRoute.route;
                QUser u = QUser.user;
                QCost c = QCost.cost;

                return queryFactory
                                .select(Projections.constructor(
                                                ScheduleWithCostDto.class,
                                                s.schedulesId,
                                                t.truckId,
                                                t.licensePlate,
                                                r.name,
                                                u.username,
                                                s.startDate,
                                                s.endDate,
                                                s.reward,
                                                c.price.sum().coalesce(BigDecimal.ZERO)))
                                .from(s)
                                .join(s.truck, t)
                                .join(s.route, r)
                                .join(s.driver, u)
                                .leftJoin(c).on(c.schedule.eq(s))
                                .where(
                                                s.startDate.between(from, to))
                                .groupBy(s.schedulesId)
                                .fetch();
        }

        @Override
        public List<TruckScheduleReportRow> reportByTruckAndRow(
                        Long userId,
                        LocalDate from,
                        LocalDate to) {
                QSchedule s = QSchedule.schedule;
                QSchedule s2 = new QSchedule("s2"); // alias subquery
                QTruck t = QTruck.truck;
                QRoute r = QRoute.route;
                QUser u = QUser.user;

                LocalDate toDate = to.plusDays(1);

                // 🔥 Tổng số chuyến của MỖI XE trong khoảng (theo user)
                JPQLQuery<Long> extraTripCountSubQuery = JPAExpressions
                                .select(s2.schedulesId.count())
                                .from(s2)
                                .where(
                                                s2.truck.eq(s.truck), // 👈 liên kết theo XE
                                                s2.driver.userId.eq(userId),
                                                s2.startDate.goe(from),
                                                s2.startDate.lt(toDate));

                return queryFactory
                                .select(Projections.constructor(
                                                TruckScheduleReportRow.class,
                                                u.username,
                                                u.fullName,
                                                t.licensePlate,
                                                t.capacity,
                                                s.startDate,
                                                r.start_point,
                                                r.end_point,
                                                extraTripCountSubQuery))
                                .from(s)
                                .join(s.truck, t)
                                .join(s.driver, u)
                                .join(s.route, r)
                                .where(
                                                u.userId.eq(userId), // ✅ USER UUID
                                                s.startDate.goe(from),
                                                s.startDate.lt(toDate))
                                .groupBy( // 🔥 GROUP BY ĐỦ CỘT
                                                u.username,
                                                u.fullName,
                                                t.licensePlate,
                                                t.capacity,
                                                s.startDate,
                                                r.start_point,
                                                r.end_point,
                                                s.truck)
                                .orderBy(
                                                t.licensePlate.asc(),
                                                s.startDate.asc())
                                .fetch();
        }

        @Override
        public Long sumExtraTripCount(
                        Long userId,
                        LocalDate from,
                        LocalDate to) {
                QSchedule s = QSchedule.schedule;

                LocalDate toDate = to.plusDays(1);

                return queryFactory
                                .select(s.schedulesId.count())
                                .from(s)
                                .where(
                                                s.driver.userId.eq(userId), // 🔥 THEO USER
                                                s.startDate.goe(from),
                                                s.startDate.lt(toDate))
                                .fetchOne();
        }

}
