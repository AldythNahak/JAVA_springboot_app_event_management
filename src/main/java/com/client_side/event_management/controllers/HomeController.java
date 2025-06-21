/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client_side.event_management.controllers;

import com.client_side.event_management.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Aldyth
 */

@Controller
public class HomeController {

    private EventService eventService;

    @Autowired
    public HomeController(EventService eventService) {
        this.eventService = eventService;
    }
    
    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("upcomming", eventService.findByStatus(new Long(1)).getAmount());
        model.addAttribute("ongoing", eventService.findByStatus(new Long(2)).getAmount());
        model.addAttribute("ended", eventService.findByStatus(new Long(3)).getAmount());
        // model.addAttribute("canceled", eventService.findByStatus(new Long(4)).getAmount());
        return "admin/dashboard";
    }
}