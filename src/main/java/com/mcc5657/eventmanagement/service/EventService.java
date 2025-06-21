package com.mcc5657.eventmanagement.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.mcc5657.eventmanagement.dto.EventDTO;
import com.mcc5657.eventmanagement.dto.SchedulerDTO;
import com.mcc5657.eventmanagement.model.Event;
import com.mcc5657.eventmanagement.model.Schedule;
import com.mcc5657.eventmanagement.model.Status;
import com.mcc5657.eventmanagement.model.response.ResponseListData;
import com.mcc5657.eventmanagement.repository.EventRepository;
import com.mcc5657.eventmanagement.repository.EventTypeRepository;
import com.mcc5657.eventmanagement.repository.ScheduleRepository;
import com.mcc5657.eventmanagement.repository.StatusRepository;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class EventService extends BasicCrudService<Event, Long> {

    private final EventRepository eventRepository;
    private final StatusRepository statusRepository;
    private final ScheduleRepository scheduleRepository;
    private final EventTypeRepository eventTypeRepository;
    private final ScheduleService scheduleService;
    private final EventTypeService eventTypeService;

    final Long statusUpComming = new Long(1);
    final Long statusOnGoing = new Long(2);
    final Long statusEnded = new Long(3);
    final Long statusCanceled = new Long(4);

    public EventService(EventRepository eventRepository, StatusRepository statusRepository, EventTypeRepository eventTypeRepository, ScheduleRepository scheduleRepository) {
        super(eventRepository);
        this.eventRepository = eventRepository;
        this.statusRepository = statusRepository;
        this.scheduleRepository = scheduleRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.scheduleService = new ScheduleService(scheduleRepository, eventRepository);
        this.eventTypeService = new EventTypeService(eventTypeRepository, scheduleRepository);
    }

    public ResponseListData<EventDTO> findAllEvent(String title) {
        if (title == null) {
            title = "";
        }
        List<EventDTO> events = new LinkedList<>();
        for (Event event : eventRepository.findByTitle(title)) {
            events.add(new EventDTO(event, eventTypeService.findAllSchedulerByEvent(event.getId())));
        }
        return new ResponseListData<>(events, new Long(events.size()));
    }

    public ResponseListData<EventDTO> findByStatus(Long status) {
        List<EventDTO> events = new LinkedList<>();
        for (Event event : eventRepository.findByStatus(status)) {
            events.add(new EventDTO(event, eventTypeService.findAllSchedulerByEvent(event.getId())));
        }
        return new ResponseListData<>(events, new Long(events.size()));
    }

    public ResponseListData<EventDTO> findAllActive() {
        List<EventDTO> events = new LinkedList<>();
        for (Event event : eventRepository.findByTwoStatus(statusUpComming, statusOnGoing)) {
            events.add(new EventDTO(event, eventTypeService.findAllSchedulerByEvent(event.getId())));
        }
        return new ResponseListData<>(events, new Long(events.size()));
    }

    public ResponseListData<EventDTO> findAllInactive() {
        List<EventDTO> events = new LinkedList<>();
        for (Event event : eventRepository.findByTwoStatus(statusEnded, statusCanceled)) {
            events.add(new EventDTO(event, eventTypeService.findAllSchedulerByEvent(event.getId())));
        }
        return new ResponseListData<>(events, new Long(events.size()));
    }
    
    public ResponseListData<EventDTO> findAllDeleted() {
        List<EventDTO> events = new LinkedList<>();
        for (Event event : eventRepository.findAllDeleted()) {
            events.add(new EventDTO(event, eventTypeService.findAllSchedulerByEvent(event.getId())));
        }
        return new ResponseListData<>(events, new Long(events.size()));
    }
    
    public ResponseListData<EventDTO> findAllOnline() {
        List<EventDTO> eventsResponse = new LinkedList<>();

        List<EventDTO> events = findAllEvent("").getData();
        ListIterator<EventDTO> itr = events.listIterator();
        while (itr.hasNext()) {
            EventDTO event = itr.next();
            for (SchedulerDTO scheduler : event.getSchedulers()) {
                if (scheduler.getIsOnline()) {
                    eventsResponse.add(event);
                    break;
                }
            }
        }
        return new ResponseListData<>(eventsResponse, new Long(eventsResponse.size()));
    }

    public ResponseListData<EventDTO> findAllOffline() {
        List<EventDTO> eventsResponse = new LinkedList<>();

        List<EventDTO> events = findAllEvent("").getData();
        ListIterator<EventDTO> itr = events.listIterator();
        while (itr.hasNext()) {
            EventDTO event = itr.next();
            for (SchedulerDTO scheduler : event.getSchedulers()) {
                if (!scheduler.getIsOnline()) {
                    eventsResponse.add(event);
                    break;
                }
            }
        }
        return new ResponseListData<>(eventsResponse, new Long(eventsResponse.size()));
    }

    public ResponseListData<EventDTO> findByDate(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);

        List<EventDTO> events = new LinkedList<>();
        LocalDateTime start;
        LocalDateTime end;
        
        for (Event event : eventRepository.findByTitle("")) {
            List<Schedule> schedules = scheduleRepository.findByEvent(event.getId());
            List<LocalDateTime> startDateTimes = new LinkedList<>();
            List<LocalDateTime> endDateTimes = new LinkedList<>();

            for (Schedule schedule : schedules) {
                startDateTimes.add(schedule.getStartTime());
                endDateTimes.add(schedule.getEndTime());
            }

            start = Collections.min(startDateTimes);
            end = Collections.max(endDateTimes);

            if (startDateTime.isBefore(start) && endDateTime.isAfter(end)) {
                events.add(new EventDTO(event, eventTypeService.findAllSchedulerByEvent(event.getId())));
            }
        }
        return new ResponseListData<>(events, new Long(events.size()));
    }

    public Event create(EventDTO request) {
        Status status = statusRepository.findById(statusUpComming).orElseThrow(() -> 
                new ResponseStatusException(HttpStatus.NOT_FOUND, "DATA NOT FOUND")
                );
        Event eventSave = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .contactPerson(request.getContactPerson())
                .registrationDeadline(request.getRegistrationDeadline())
                .quota(request.getQuota())
                .participantQuantity(new Long(0))
                .isDelete(false)
                .image(Base64.getDecoder().decode(request.getImage()))
                .status(status)
                .build();
        eventSave = eventRepository.save(eventSave);
        Schedule schedule;
        for (SchedulerDTO scheduler : request.getSchedulers()) {
            schedule = scheduleService.create(scheduler, eventSave.getId());
            eventTypeService.create(scheduler, schedule.getId());
        }
        return eventSave;
    }

    public Event update(EventDTO request, Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> 
                new ResponseStatusException(HttpStatus.NOT_FOUND, "DATA NOT FOUND")
                );
        if (event.getIsDelete()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "DATA HAS BEEN DELETED");
        }
        Status status = event.getStatus();
        Event eventSave = Event.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .contactPerson(request.getContactPerson())
                .registrationDeadline(request.getRegistrationDeadline())
                .quota(request.getQuota())
                .participantQuantity(event.getParticipantQuantity())
                .isDelete(event.getIsDelete())
                .image(Base64.getDecoder().decode(request.getImage()))
                .status(status)
                .build();
        Schedule schedule;
        for (SchedulerDTO scheduler : request.getSchedulers()) {
            if (scheduler.getId()!=null) {
                eventTypeService.update(scheduler, scheduler.getId());
                scheduleService.update(scheduler, scheduler.getId());
            } else {
                schedule = scheduleService.create(scheduler, eventSave.getId());
                eventTypeService.create(scheduler, schedule.getId());
            }
        }
        return eventRepository.save(eventSave);
    }

    public EventDTO deleteEvent(Long id) {
        Event event = findById(id);
        event.setIsDelete(true);
        event = eventRepository.save(event);
        return new EventDTO(event, eventTypeService.findAllSchedulerByEvent(event.getId()));
    }
    
    @Deprecated
    public EventDTO deleteEventPermanent(Long id) {
        List<SchedulerDTO> schedulers = new LinkedList<>();
        for (Schedule schedule : scheduleRepository.findByEvent(id)) {
            schedulers.add(new SchedulerDTO(
                    eventTypeRepository.findById(schedule.getId()).get(), 
                    scheduleRepository.findById(schedule.getId()).get()));
            eventTypeRepository.deleteById(schedule.getId());
            scheduleRepository.deleteById(schedule.getId());
        }
        return new EventDTO(delete(id), schedulers);
    }

    @Scheduled(cron = "0 * * * * *")
    private void updateStatus() {
        Status onGoing = statusRepository.findById(statusOnGoing).orElseThrow(() -> 
                new ResponseStatusException(HttpStatus.NOT_FOUND, "DATA NOT FOUND")
                );;
        Status ended = statusRepository.findById(statusEnded).orElseThrow(() -> 
                new ResponseStatusException(HttpStatus.NOT_FOUND, "DATA NOT FOUND")
                );;

        LocalDateTime startDate;
        LocalDateTime endDate;
        for (Event event : eventRepository.findByTwoStatus(statusUpComming, statusOnGoing)) {
            try {
                List<Schedule> schedules = scheduleRepository.findByEvent(event.getId());
                List<LocalDateTime> startDateTimes = new LinkedList<>();
                List<LocalDateTime> endDateTimes = new LinkedList<>();

                for (Schedule schedule : schedules) {
                    startDateTimes.add(schedule.getStartTime());
                    endDateTimes.add(schedule.getEndTime());
                }

                startDate = Collections.min(startDateTimes);
                endDate = Collections.max(endDateTimes);

                if (LocalDateTime.now().isAfter(endDate)) {
                    event.setStatus(ended);
                    eventRepository.save(event);
                } else if (LocalDateTime.now().isAfter(startDate)) {
                    event.setStatus(onGoing);
                    eventRepository.save(event);
                }
            } catch (Exception e) {
            }
        }
    }

}
