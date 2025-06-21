/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client_side.event_management.controllers;

import com.client_side.event_management.models.Event;
import com.client_side.event_management.services.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Aldyth
 */

@Controller
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    public String eventDetail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("data", eventService.findByIdEvent(id).getData());
        return "user/event-detail";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("{id}/edit")
    public String editEvent(@PathVariable("id") Long idEvent, Model model) {
        model.addAttribute("idEvent", idEvent);
        return "admin/create";
    }

    @GetMapping("{id}/checkout")
    public String checkout(@PathVariable("id") Long idEvent, Model model) {
        model.addAttribute("idEvent", idEvent);
        return "user/checkout-event";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("{id}/participant")
    public String participant(@PathVariable("id") Long idEvent, Model model) {
        model.addAttribute("idEvent", idEvent);
        model.addAttribute("event", eventService.findByIdEvent(idEvent).getData());
        return "admin/participant-list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/create")
    public String createEvent(Event event) {
        return "admin/create";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/eventlist")
    public String listEvent() {
        return "admin/event-list";
    }
}