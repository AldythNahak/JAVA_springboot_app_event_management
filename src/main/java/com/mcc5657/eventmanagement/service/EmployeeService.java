package com.mcc5657.eventmanagement.service;

import com.mcc5657.eventmanagement.model.Employee;
import com.mcc5657.eventmanagement.repository.EmployeeRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmployeeService extends BasicCrudService<Employee, Long> {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Employee findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Not Found"));
    }

}