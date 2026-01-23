package com.example.TransportHC.repository.schedule;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TransportHC.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID>, ScheduleRepositoryCustom {}
