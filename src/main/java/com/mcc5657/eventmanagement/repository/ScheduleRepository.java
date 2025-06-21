package com.mcc5657.eventmanagement.repository;

import java.util.List;

import com.mcc5657.eventmanagement.model.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(value = "SELECT * FROM schedule WHERE id_event=?1", nativeQuery = true)
    List<Schedule> findByEvent(Long idEvent);
}
