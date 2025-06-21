package com.mcc5657.eventmanagement.repository;

import com.mcc5657.eventmanagement.model.Employee;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    Optional<Employee> findByEmail(String email);
}