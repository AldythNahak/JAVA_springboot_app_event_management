/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client_side.event_management.controllers;

import java.security.Principal;

import com.client_side.event_management.models.CertificateRequest;
import com.client_side.event_management.services.CertificateService;
import com.client_side.event_management.services.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Aldyth
 */

@Controller
@RequestMapping("/ticket")
public class TicketController {

    private EmployeeService employeeService;
    private CertificateService certificateService;

    @Autowired
    public TicketController(EmployeeService employeeService, CertificateService certificateService) {
        this.employeeService = employeeService;
        this.certificateService = certificateService;
    }

    @GetMapping
    public String index() {
        return "redirect:/ticket/ticketlist";
    }

    @GetMapping("/ticketlist")
    public String listTicket(Principal principal, Model model) {
        model.addAttribute("data", employeeService.findByEmail(principal.getName()).getData());
        return "user/ticket-list";
    }

    @GetMapping("/certificate")
    public String create(@RequestParam("ticket") String idTicket, Model model) {
        model.addAttribute("id", idTicket);
        model.addAttribute("data", certificateService.create(new CertificateRequest(idTicket)).getData());
        return "user/certificate";
    }
}