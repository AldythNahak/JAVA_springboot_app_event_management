package com.mcc5657.eventmanagement.controller;

import javax.validation.Valid;

import com.mcc5657.eventmanagement.dto.EventDTO;
import com.mcc5657.eventmanagement.dto.SchedulerDTO;
import com.mcc5657.eventmanagement.model.Event;
import com.mcc5657.eventmanagement.model.Schedule;
import com.mcc5657.eventmanagement.model.response.ResponseData;
import com.mcc5657.eventmanagement.model.response.ResponseListData;
import com.mcc5657.eventmanagement.model.response.ResponseMessage;
import com.mcc5657.eventmanagement.repository.EventRepository;
import com.mcc5657.eventmanagement.repository.EventTypeRepository;
import com.mcc5657.eventmanagement.repository.ScheduleRepository;
import com.mcc5657.eventmanagement.repository.StatusRepository;
import com.mcc5657.eventmanagement.service.EventService;
import com.mcc5657.eventmanagement.service.EventTypeService;
import com.mcc5657.eventmanagement.service.ScheduleService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;
    private final ScheduleService scheduleService;
    private final EventTypeService eventTypeService;

    public EventController(EventRepository eventRepository, StatusRepository statusRepository,
            ScheduleRepository scheduleRepository, EventTypeRepository eventTypeRepository) {
        this.eventService = new EventService(eventRepository, statusRepository, eventTypeRepository,
                scheduleRepository);
        this.scheduleService = new ScheduleService(scheduleRepository, eventRepository);
        this.eventTypeService = new EventTypeService(eventTypeRepository, scheduleRepository);
    }

    // @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PreAuthorize("permitAll()")
    @GetMapping()
    public ResponseListData<EventDTO> findAllEvent(
            @RequestParam(name = "title", required = false) String title) {
        return eventService.findAllEvent(title);
    }

    // @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PreAuthorize("permitAll()")
    @GetMapping("/status/{status}")
    public ResponseListData<EventDTO> findByStatus (@PathVariable("status") Long status) {
        return eventService.findByStatus(status);
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("permitAll()")
    @GetMapping("active")
    public ResponseListData<EventDTO> findAllActive() {
        return eventService.findAllActive();
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("permitAll()")
    @GetMapping("inactive")
    public ResponseListData<EventDTO> findAllInactive() {
        return eventService.findAllInactive();
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("permitAll()")
    @GetMapping("deleted")
    public ResponseListData<EventDTO> findAllDeleted() {
        return eventService.findAllDeleted();
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("permitAll()")
    @GetMapping("online")
    public ResponseListData<EventDTO> findAllOnline() {
        return eventService.findAllOnline();
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("permitAll()")
    @GetMapping("offline")
    public ResponseListData<EventDTO> findAllOffline() {
        return eventService.findAllOffline();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("date")
    public ResponseListData<EventDTO> findByDate(@RequestParam("start-date") String startDate,
            @RequestParam("end-date") String endDate) {
        return eventService.findByDate(startDate, endDate);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseData<EventDTO> findByIdEvent(@PathVariable("id") Long id) {
        return new ResponseData<>(
                new EventDTO(eventService.findById(id), eventTypeService.findAllSchedulerByEvent(id)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseMessage<EventDTO> createEvent(@Valid @RequestBody EventDTO eventRequest) {
        Event event = eventService.create(eventRequest);
        return new ResponseMessage<>(new EventDTO(event, eventTypeService.findAllSchedulerByEvent(event.getId())),
                "Event created");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseMessage<EventDTO> updateEvent(@PathVariable("id") Long id,
            @Valid @RequestBody EventDTO eventRequest) {
        Event event = eventService.update(eventRequest, id);
        return new ResponseMessage<>(new EventDTO(event, eventTypeService.findAllSchedulerByEvent(event.getId())),
                "Event updated");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{id}")
    public ResponseMessage<EventDTO> deleteEvent(@PathVariable("id") Long id) {
        return new ResponseMessage<>(eventService.deleteEvent(id),"Event deleted");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("manage-delete/{id}")
    public ResponseMessage<EventDTO> delete(@PathVariable("id") Long id) {
        return new ResponseMessage<>(eventService.deleteEventPermanent(id), "Event deleted");
    }
}
