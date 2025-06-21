/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client_side.event_management.controllers.ajax;

import com.client_side.event_management.models.Event;
import com.client_side.event_management.models.response.ResponseData;
import com.client_side.event_management.models.response.ResponseListData;
import com.client_side.event_management.models.response.ResponseMessage;
import com.client_side.event_management.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Aldyth
 */
@Controller
@RequestMapping("/ajax/event")
public class EventsController {

    private final EventService eventService;

    @Autowired
    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public @ResponseBody ResponseListData<Event> findAllEvent(
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "title", required = false) String title) {
        if (type == null) {
            return eventService.findAllEvent(title);
        } else {
            return eventService.findByType(type);
        }
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseData<Event> findByIdEvent(@PathVariable("id") Long id) {
        return eventService.findByIdEvent(id);
    }

    @PostMapping
    public @ResponseBody ResponseMessage<Event> createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseMessage<Event> updateEvent(@RequestBody Event event, @PathVariable("id") Long id) {
        return eventService.updateEvent(event, id);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseMessage<Event> deleteEvent(@PathVariable("id") Long id) {
        return eventService.deleteEvent(id);
    }
}
