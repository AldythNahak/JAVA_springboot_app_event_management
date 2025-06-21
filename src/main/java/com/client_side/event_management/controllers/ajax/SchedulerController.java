/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client_side.event_management.controllers.ajax;

import java.util.List;

import com.client_side.event_management.models.SchedulerRequest;
import com.client_side.event_management.models.response.ResponseData;
import com.client_side.event_management.models.response.ResponseListData;
import com.client_side.event_management.models.response.ResponseMessage;
import com.client_side.event_management.services.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Aldyth
 */
@Controller
@RequestMapping("/ajax/scheduler")
public class SchedulerController {

    private SchedulerService schedulerService;

    @Autowired
    public SchedulerController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @GetMapping
    public @ResponseBody ResponseListData<SchedulerRequest> findAllScheduler() {
        return schedulerService.findAllScheduler();
    }

    @GetMapping("/event/{id}")
    public @ResponseBody ResponseListData<SchedulerRequest> findAllSchedulerByEvent(@PathVariable("id") Long idEvent) {
        return schedulerService.findAllSchedulerByEvent(idEvent);
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseData<SchedulerRequest> findByIdScheduler(@PathVariable("id") Long id) {
        return schedulerService.findByIdScheduler(id);
    }

    @GetMapping("/year")
    public @ResponseBody List<Long> findSchedulePerMonth() {
        return schedulerService.findSchedulePerMonth().getData();
    }

    @PostMapping
    public @ResponseBody ResponseMessage<SchedulerRequest> createSchedule(@RequestBody SchedulerRequest scheduler) {
        return schedulerService.createSchedule(scheduler);
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseMessage<SchedulerRequest> updateSchedule(@RequestBody SchedulerRequest scheduler,
            @PathVariable("id") Long id) {
        return schedulerService.updateSchedule(scheduler, id);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseMessage<SchedulerRequest> deleteSchedule(@PathVariable("id") Long id) {
        return schedulerService.deleteSchedule(id);
    }
}
