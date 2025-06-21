/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client_side.event_management.controllers.ajax;

import com.client_side.event_management.models.Employee;
import com.client_side.event_management.models.response.ResponseData;
import com.client_side.event_management.models.response.ResponseMessage;
import com.client_side.event_management.services.EmployeeService;
import com.client_side.event_management.services.LoginService;

import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Aldyth
 */

@Controller
@RequestMapping("/ajax/auth")
public class AuthenticationController {

    private LoginService loginService;
    private EmployeeService employeeService;

    @Autowired
    public AuthenticationController(LoginService loginService, EmployeeService employeeService) {
        this.loginService = loginService;
        this.employeeService = employeeService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register")
    public @ResponseBody ResponseMessage<Employee> create(@RequestBody Employee employee) {
        return loginService.register(employee);
    }

    @PostMapping("/forget-password")
    public @ResponseBody ResponseMessage<Employee> forgetPassword(@RequestBody Map<String, String> request) {
        return loginService.forgetPassword(request);
    }

    @GetMapping("/username")
    @ResponseBody
    public ResponseData<Employee> currentUserName(Principal principal) {
        return employeeService.findByEmail(principal.getName());
    }

    @PostMapping("/create-password")
    public @ResponseBody
    ResponseMessage<Employee> createPassword(@RequestBody Map<String, String> request) {
        return loginService.createPassword(request);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/change-password")
    public @ResponseBody
    ResponseMessage<Employee> changePassword(@RequestBody Map<String, String> request) {
        return loginService.changePassword(request);
    }

}