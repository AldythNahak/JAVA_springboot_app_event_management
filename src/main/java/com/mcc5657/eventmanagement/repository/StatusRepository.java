package com.mcc5657.eventmanagement.repository;

import java.util.Optional;

import com.mcc5657.eventmanagement.model.Status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatusRepository extends JpaRepository<Status, Long> {
    
    @Query(value = "SELECT * FROM status WHERE status_name = ?1", nativeQuery = true)
    Optional<Status> findByStatusName(String statusName);
}
