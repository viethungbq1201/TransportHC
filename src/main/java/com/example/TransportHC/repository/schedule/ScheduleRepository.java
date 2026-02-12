package com.example.TransportHC.repository.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TransportHC.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {
}
