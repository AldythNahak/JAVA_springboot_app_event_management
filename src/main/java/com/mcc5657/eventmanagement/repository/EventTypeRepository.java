package com.mcc5657.eventmanagement.repository;

import com.mcc5657.eventmanagement.model.EventType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {
    
}
