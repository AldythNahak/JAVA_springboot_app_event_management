package com.mcc5657.eventmanagement.service;

import java.util.LinkedList;
import java.util.List;

import com.mcc5657.eventmanagement.dto.SchedulerDTO;
import com.mcc5657.eventmanagement.model.EventType;
import com.mcc5657.eventmanagement.model.Schedule;
import com.mcc5657.eventmanagement.repository.EventTypeRepository;
import com.mcc5657.eventmanagement.repository.ScheduleRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EventTypeService extends BasicCrudService<EventType, Long> {

    private final EventTypeRepository eventTypeRepository;
    private final ScheduleRepository scheduleRepository;

    public EventTypeService(EventTypeRepository eventTypeRepository, ScheduleRepository scheduleRepository) {
        super(eventTypeRepository);
        this.eventTypeRepository = eventTypeRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public EventType create(SchedulerDTO request, Long idSchedule) {
        Schedule schedule = scheduleRepository.findById(idSchedule)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "DATA NOT FOUND"));
        EventType eventTypeSave = EventType.builder()
                .schedule(schedule)
                .isOnline(request.getIsOnline())
                .linkPlatform(request.getLinkPlatform())
                .build();
        return eventTypeRepository.save(eventTypeSave);
    }

    public EventType update(SchedulerDTO request, Long id) {
        EventType eventType = eventTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "DATA NOT FOUND"));
        eventType.setIsOnline(request.getIsOnline());
        eventType.setLinkPlatform(request.getLinkPlatform());
        return eventTypeRepository.save(eventType);
    }
    
    public List<SchedulerDTO> findAllScheduler() {
        List<SchedulerDTO> schedulers = new LinkedList<>();
        for (Schedule schedule : scheduleRepository.findAll()) {
            schedulers.add(new SchedulerDTO(findById(schedule.getId()), schedule));
        }
        return schedulers;
    }
    
    public List<SchedulerDTO> findAllSchedulerByEvent(Long idEvent) {
        List<SchedulerDTO> schedulers = new LinkedList<>();
        for (Schedule schedule : scheduleRepository.findByEvent(idEvent)) {
            schedulers.add(new SchedulerDTO(findById(schedule.getId()), schedule));
        }
        return schedulers;
    }
}
