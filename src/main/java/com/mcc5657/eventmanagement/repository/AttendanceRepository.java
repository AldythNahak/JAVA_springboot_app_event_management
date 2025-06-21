package com.mcc5657.eventmanagement.repository;

import java.util.List;
import java.util.Optional;

import com.mcc5657.eventmanagement.model.Attendance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query(value = "SELECT * FROM attendance WHERE id_ticket = ?1 AND id_schedule = ?2", nativeQuery = true)
    Optional<Attendance> findByTicketAndSchedule(String idTicket, Long idSchedule);

    @Query(value = "SELECT * FROM attendance WHERE id_schedule = ?1", nativeQuery = true)
    List<Attendance> findByIdSchedule(Long idSchedule);

    @Query(value = "SELECT * FROM attendance WHERE id_ticket = ?1", nativeQuery = true)
    Optional<Attendance> findByTicket(String idTicket);
}