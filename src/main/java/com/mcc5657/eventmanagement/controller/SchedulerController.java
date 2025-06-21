package com.mcc5657.eventmanagement.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import com.mcc5657.eventmanagement.dto.SchedulerDTO;
import com.mcc5657.eventmanagement.model.EventType;
import com.mcc5657.eventmanagement.model.Schedule;
import com.mcc5657.eventmanagement.model.response.ResponseData;
import com.mcc5657.eventmanagement.model.response.ResponseListData;
import com.mcc5657.eventmanagement.model.response.ResponseMessage;
import com.mcc5657.eventmanagement.repository.EventRepository;
import com.mcc5657.eventmanagement.repository.EventTypeRepository;
import com.mcc5657.eventmanagement.repository.ScheduleRepository;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scheduler")
public class SchedulerController {
    
    private final ScheduleService scheduleService;
    private final EventTypeService eventTypeService;

    public SchedulerController(EventRepository eventRepository,
            ScheduleRepository scheduleRepository, 
            EventTypeRepository eventTypeRepository) {
        this.scheduleService = new ScheduleService(scheduleRepository, eventRepository);
        this.eventTypeService = new EventTypeService(eventTypeRepository, scheduleRepository);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping()
    public ResponseListData<SchedulerDTO> findAllScheduler() {
        return new ResponseListData<>(eventTypeService.findAllScheduler());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/event/{id}")
    public ResponseListData<SchedulerDTO> findAllSchedulerByEvent(@PathVariable("id") Long idEvent) {
        return new ResponseListData<>(eventTypeService.findAllSchedulerByEvent(idEvent));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseData<SchedulerDTO> findByIdScheduler(@PathVariable("id") Long id) {
        return new ResponseData<>(new SchedulerDTO(eventTypeService.findById(id), scheduleService.findById(id)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("year")
    public ResponseListData<Long> findSchedulePerMonth() {
        return new ResponseListData<>(scheduleService.findSchedulePerMonth());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseMessage<SchedulerDTO> createSchedule(
                @Valid @RequestBody SchedulerDTO scheduler) {
        Schedule schedule = scheduleService.create(scheduler, scheduler.getIdEvent());
        EventType eventType = eventTypeService.create(scheduler, schedule.getId());
        return new ResponseMessage<>(new SchedulerDTO(eventType, schedule), "Event created");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseMessage<SchedulerDTO> updateSchedule(
            @PathVariable("id") Long id,
            @Valid @RequestBody SchedulerDTO request) {
        return new ResponseMessage<>(
                new SchedulerDTO(eventTypeService.update(request, id), scheduleService.update(request, id)), 
                "Schedule updated");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseMessage<SchedulerDTO> deleteSchedule(
            @PathVariable("id") Long id) {
        return new ResponseMessage<>(
                new SchedulerDTO(eventTypeService.delete(id), scheduleService.delete(id)), 
                "Schedule deleted");
    }

}
