package com.mcc5657.eventmanagement.controller;

import com.mcc5657.eventmanagement.dto.TicketRequest;
import com.mcc5657.eventmanagement.dto.TicketResponse;
import com.mcc5657.eventmanagement.model.response.ResponseListData;
import com.mcc5657.eventmanagement.model.response.ResponseMessage;
import com.mcc5657.eventmanagement.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    private TicketService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseListData<TicketResponse> getByEvent(@RequestParam("event") Long idEvent, @RequestParam("schedule") Long idSchedule) {
        if (idSchedule == null) {
            return new ResponseListData<>(service.findByEvent(idEvent));
        } else {
            return new ResponseListData<>(service.findBySchedule(idEvent, idSchedule));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping
    public ResponseMessage<TicketResponse> create(@RequestBody TicketRequest ticket) {
        return new ResponseMessage<>(service.createTicket(ticket), "Successfully register new ticket");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/myticket")
    public ResponseListData<TicketResponse> getByEmployee(@RequestParam("employee") Long idEmployee) {
        return new ResponseListData<>(service.findByEmployee(idEmployee));
    }

}
