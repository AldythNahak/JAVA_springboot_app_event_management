/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client_side.event_management.controllers.ajax;

import com.client_side.event_management.models.TicketRequest;
import com.client_side.event_management.models.TicketResponse;
import com.client_side.event_management.models.response.ResponseListData;
import com.client_side.event_management.models.response.ResponseMessage;
import com.client_side.event_management.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Aldyth
 */
@Controller
@RequestMapping("/ajax/ticket")
public class TicketsController {

    private TicketService ticketService;

    @Autowired
    public TicketsController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/event/{id}")
    public @ResponseBody ResponseListData<TicketResponse> findByEvent(@PathVariable("id") Long idEvent,
            @RequestParam(name = "schedule" , required = false) Long idSchedule) {
        if (idSchedule == null) {
            return ticketService.findByEvent(idEvent);
        } else {
            return ticketService.findBySchedule(idEvent, idSchedule);
        }
    }

    @GetMapping("/employee/{id}")
    public @ResponseBody ResponseListData<TicketResponse> findByEmployee(@PathVariable("id") Long idEmployee) {
        return ticketService.findByEmployee(idEmployee);
    }

    @PostMapping
    public @ResponseBody ResponseMessage<TicketResponse> create(@RequestBody TicketRequest ticket) {
        return ticketService.create(ticket);
    }

}