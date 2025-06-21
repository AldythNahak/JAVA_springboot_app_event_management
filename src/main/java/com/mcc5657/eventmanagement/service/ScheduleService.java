package com.mcc5657.eventmanagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mcc5657.eventmanagement.dto.SchedulerDTO;
import com.mcc5657.eventmanagement.model.Event;
import com.mcc5657.eventmanagement.model.Schedule;
import com.mcc5657.eventmanagement.repository.EventRepository;
import com.mcc5657.eventmanagement.repository.ScheduleRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ScheduleService extends BasicCrudService<Schedule, Long> {
    
    private final ScheduleRepository scheduleRepository;
    private final EventRepository eventRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, EventRepository eventRepository) {
        super(scheduleRepository);
        this.scheduleRepository = scheduleRepository;
        this.eventRepository = eventRepository;
    }
    
    public ArrayList<Long> findSchedulePerMonth() {
        Integer year = LocalDateTime.now().getYear();
        ArrayList<Long> schedPerMonth = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            schedPerMonth.add(new Long(0));
        }
        for (Schedule schedule : findAll()) {
            if (schedule.getStartTime().getYear()==year) {
                schedPerMonth.set(
                        schedule.getStartTime().getMonthValue()-1, 
                        schedPerMonth.get(schedule.getStartTime().getMonthValue()-1)+1);
            }
            if (schedule.getStartTime().getMonthValue() != schedule.getEndTime().getMonthValue()
                    && schedule.getEndTime().getYear()==year) {
                schedPerMonth.set(
                        schedule.getEndTime().getMonthValue()-1, 
                        schedPerMonth.get(schedule.getEndTime().getMonthValue()-1)+1);
            }
        }
        return schedPerMonth;
    }
    
    public Schedule create(SchedulerDTO request, Long idEvent) {
        Event event = eventRepository.findById(idEvent).orElseThrow(() -> 
                new ResponseStatusException(HttpStatus.NOT_FOUND, "DATA NOT FOUND")
                );
        Schedule scheduleSave = Schedule.builder()
                .event(event)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .location(request.getLocation())
                .build();
        return scheduleRepository.save(scheduleSave);
    }
    
    public Schedule update(SchedulerDTO request, Long id) {
        Schedule schedule= scheduleRepository.findById(id).orElseThrow(() -> 
                new ResponseStatusException(HttpStatus.NOT_FOUND, "DATA NOT FOUND")
                );
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setLocation(request.getLocation());
        return scheduleRepository.save(schedule);
    }

}
