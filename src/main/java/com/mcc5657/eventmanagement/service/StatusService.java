package com.mcc5657.eventmanagement.service;

import com.mcc5657.eventmanagement.model.Status;
import com.mcc5657.eventmanagement.repository.StatusRepository;

public class StatusService extends BasicCrudService<Status, Long> {
    
    private final StatusRepository repository;

    public StatusService(StatusRepository repository) {
        super(repository);
        this.repository = repository;
    }
    
}
