package com.mcc5657.eventmanagement.controller;

import javax.validation.Valid;

import com.mcc5657.eventmanagement.model.Employee;
import com.mcc5657.eventmanagement.model.response.ResponseData;
import com.mcc5657.eventmanagement.model.response.ResponseListData;
import com.mcc5657.eventmanagement.model.response.ResponseMessage;
import com.mcc5657.eventmanagement.repository.EmployeeRepository;
import com.mcc5657.eventmanagement.service.EmployeeService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeRepository repository) {
        this.service = new EmployeeService(repository);
    }

    @GetMapping("/all")
    public ResponseListData<Employee> findAll() {
        return new ResponseListData<>(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseData<Employee> findById(@PathVariable("id") Long id) {
        return new ResponseData<>(service.findById(id));
    }

    @GetMapping
    public ResponseData<Employee> findByEmail(@RequestParam("email") String email) {
        return new ResponseData<>(service.findByEmail(email));
    }

    @PostMapping
    public ResponseMessage<Employee> create(@Valid @RequestBody Employee employee) {
        return new ResponseMessage<>(service.create(employee), "Employee Registered");
    }

    @PutMapping("/{id}")
    public ResponseMessage<Employee> update(@Valid @RequestBody Employee employee, @PathVariable("id") Long id) {
        return new ResponseMessage<>(service.update(employee, id), "Employee Updated");
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<Employee> delete(@PathVariable("id") Long id) {
        return new ResponseMessage<>(service.delete(id), "Employee Deleted");
    }
}