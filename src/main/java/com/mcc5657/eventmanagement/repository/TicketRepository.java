package com.mcc5657.eventmanagement.repository;

import java.util.List;
import java.util.Optional;

import com.mcc5657.eventmanagement.model.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TicketRepository extends JpaRepository<Ticket, String> {

    @Query(value = "SELECT * FROM ticket WHERE id_event = ?1 AND id_employee=?2", nativeQuery = true)
    Optional<Ticket> findByEventAndEmployee(Long idEvent, Long idEmployee);

    @Query(value = "SELECT * FROM ticket WHERE id_employee=?1", nativeQuery = true)
    List<Ticket> findByEmployee(Long idEmployee);

    @Query(value = "SELECT * FROM ticket WHERE id_event=?1", nativeQuery = true)
    List<Ticket> findByEvent(Long idEvent);
}
