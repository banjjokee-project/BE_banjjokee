package com.project.BE_banjjokee.repository;

import com.project.BE_banjjokee.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByPetId(Long petId);
    List<Schedule> findByIdIn(List<Long> ids);
    List<Schedule> findAllByDate(LocalDate date);
}
