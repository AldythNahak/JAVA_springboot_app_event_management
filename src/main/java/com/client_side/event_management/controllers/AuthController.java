/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client_side.event_management.controllers;

import com.client_side.event_management.models.Employee;
import com.client_side.event_management.models.LoginRequest;
import com.client_side.event_management.services.LoginService;
import com.client_side.event_management.utils.GetAuthContext;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Aldyth
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private LoginService loginService;

    @Autowired
    public AuthController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String loginPage(LoginRequest loginRequest, Employee employee) {
        Authentication auth = GetAuthContext.getAuthorization();

        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "auth/login";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("LoginRequest") LoginRequest loginRequest, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/auth/login";
        }

        if (!loginService.login(loginRequest)) {
            return "redirect:/auth/login?error=true";
        }

        return "redirect:/";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/register")
    public String registerEmployee(Employee employee) {
        return "auth/register";
    }
    
    @GetMapping("/create-password")
    public String createPassword(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "auth/create-password";
    }
    
    @GetMapping("/change-password")
    public String changePassword(Model model) {
        return "auth/change-password";
    }
    
    @PostMapping("/logout")
    public String logout() {
        loginService.logout();
        return "redirect:/auth/login?logout=true";
    }
}