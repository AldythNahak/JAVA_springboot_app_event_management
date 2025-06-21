/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc5657.eventmanagement.controller;

import com.mcc5657.eventmanagement.dto.LoginRequest;
import com.mcc5657.eventmanagement.dto.LoginResponse;
import com.mcc5657.eventmanagement.model.Account;
import com.mcc5657.eventmanagement.model.Employee;
import com.mcc5657.eventmanagement.model.response.ResponseMessage;
import com.mcc5657.eventmanagement.service.AccountService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kintan semi
 */

@RestController
@RequestMapping("/api/auth")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseMessage<Employee> register(@RequestBody Employee employee) {
        return new ResponseMessage<>(accountService.registration(employee), "Account Created");
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/create-password")
    public ResponseMessage<Employee> createPassword(@RequestBody Map<String, String> request) {
        return new ResponseMessage<>(
                accountService.changePasswordVerification(request.get("token"), request.get("password")),
                "Password Created");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/change-password")
    public ResponseMessage<Employee> changePassword(@RequestBody Map<String, String> request) {
        return new ResponseMessage<>(
                accountService.changePassword(request.get("password")),
                "Password Created");
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/forget-password")
    public ResponseMessage<Employee> forgetPassword(@RequestBody Map<String, String> request) {
        return new ResponseMessage<>(accountService.forgetPassword(request.get("email")), "Request Accepted");
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseMessage<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseMessage<>(accountService.login(loginRequest), "Logged In");
    }

}
