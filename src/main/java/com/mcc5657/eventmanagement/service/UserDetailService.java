/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc5657.eventmanagement.service;

import com.mcc5657.eventmanagement.model.Account;
import com.mcc5657.eventmanagement.model.Employee;
import com.mcc5657.eventmanagement.model.UserDetail;
import com.mcc5657.eventmanagement.repository.AccountRepository;
import com.mcc5657.eventmanagement.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author kintan semi
 */
@Service
public class UserDetailService implements UserDetailsService{
   
    private final AccountRepository accountRepository;
    private final EmployeeRepository employeeRepository;

    public UserDetailService(AccountRepository accountRepository, EmployeeRepository employeeRepository) {
        this.accountRepository = accountRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmail(string)
                .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));
        Account account = accountRepository.findById(employee.getId())
                .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));
        return new UserDetail(account);
    }
}
