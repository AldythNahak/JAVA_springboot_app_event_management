package com.mcc5657.eventmanagement.service;

import java.util.List;

import com.mcc5657.eventmanagement.model.BasicCrudModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class BasicCrudService<E extends BasicCrudModel<E,I>, I> implements BasicCrudServiceInterface<E, I> {

    private final JpaRepository<E, I> repository;

    public BasicCrudService(JpaRepository<E, I> repository) {
        this.repository = repository;
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public E findById(I id) {
        return repository.findById(id).orElseThrow(() -> 
                new ResponseStatusException(HttpStatus.NOT_FOUND, "DATA NOT FOUND")
                );
    }

    @Override
    public E create(E data) {
        if (data.getId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "DATA ALREADY EXIST");
        }
        return repository.save(data);
    }

    @Override
    public E update(E data, I id) {
        data.setId(id);
        repository.findById(id).orElseThrow(() -> 
                new ResponseStatusException(HttpStatus.NOT_FOUND, "DATA NOT FOUND")
                );
        return repository.save(data);
    }

    @Override
    public E delete(I id) {
        E data = repository.findById(id).orElseThrow(() -> 
                new ResponseStatusException(HttpStatus.NOT_FOUND, "DATA NOT FOUND")
                );
        repository.deleteById(id);
        return data;
    }
}