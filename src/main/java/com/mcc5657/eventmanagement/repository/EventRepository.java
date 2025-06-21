package com.mcc5657.eventmanagement.repository;

import java.util.List;

import com.mcc5657.eventmanagement.model.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Long> {
    
    @Query(value = "SELECT * FROM event WHERE is_delete = true ORDER BY id DESC", nativeQuery = true)
    List<Event> findAllDeleted();

    @Query(value = "SELECT * FROM event WHERE is_delete = false AND id_status = ?1 ORDER BY id DESC", nativeQuery = true)
    List<Event> findByStatus(Long status);

    @Query(value = "SELECT * FROM event WHERE is_delete = false AND (id_status = ?1 OR id_status = ?2) ORDER BY id DESC", nativeQuery = true)
    List<Event> findByTwoStatus(Long status1, Long status2);

    @Query(value = "SELECT * FROM event WHERE is_delete = false AND title LIKE %?1% ORDER BY id DESC", nativeQuery = true)
    List<Event> findByTitle(String title);

}
