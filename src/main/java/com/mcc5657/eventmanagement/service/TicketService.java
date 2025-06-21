package com.mcc5657.eventmanagement.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mcc5657.eventmanagement.dto.TicketRequest;
import com.mcc5657.eventmanagement.dto.TicketResponse;
import com.mcc5657.eventmanagement.model.Employee;
import com.mcc5657.eventmanagement.model.Event;
import com.mcc5657.eventmanagement.model.Schedule;
import com.mcc5657.eventmanagement.model.Ticket;
import com.mcc5657.eventmanagement.repository.AttendanceRepository;
import com.mcc5657.eventmanagement.repository.EmployeeRepository;
import com.mcc5657.eventmanagement.repository.EventRepository;
import com.mcc5657.eventmanagement.repository.ScheduleRepository;
import com.mcc5657.eventmanagement.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TicketService {

    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;

    public TicketResponse createTicket(TicketRequest request) {
        // Check if already register
        if (ticketRepository.findByEventAndEmployee(request.getIdEvent(), request.getIdEmployee()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You are already registered for this event");
        }

        Event event = eventRepository.findById(request.getIdEvent())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (event.getQuota() == event.getParticipantQuantity()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant quota is full");
        }

        Employee employee = employeeRepository.findById(request.getIdEmployee())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Schedule> schedules = scheduleRepository.findByEvent(event.getId());

        Long participantQuantity = event.getParticipantQuantity();

        LocalDateTime registrationDate = LocalDateTime.now();
        if (registrationDate.isAfter(event.getRegistrationDeadline())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event is already ended");
        }

        String formatIdEvent = String.format("%1$4s", event.getId()).replace(' ', '0');
        String formatParticipantQty = String.format("%1$4s", participantQuantity + 1).replace(' ', '0');
        String id = String.format("T%s%s%s", DateTimeFormatter.ofPattern("ddMMyyyy").format(registrationDate),
                formatIdEvent, formatParticipantQty);

        Ticket ticket = Ticket.builder().id(id).event(event).employee(employee).registrationDate(registrationDate)
                .build();

        String link = "http://localhost:8089/attendance?ticket=" + id;

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", employee.getName());
        model.put("event", event.getTitle());
        model.put("schedules", schedules);
        model.put("link", link);

        emailSenderService.sendTicketMail(employee.getEmail(), model);

        System.out.println("[SUCCESS] Registration No. " + id);
        event.setParticipantQuantity(participantQuantity + 1);
        eventRepository.save(event);
        return new TicketResponse(ticketRepository.save(ticket));
    }

    public List<TicketResponse> findByEmployee(Long idEmployee) {
        List<TicketResponse> tickets = new ArrayList<>();

        for (Ticket ticket : ticketRepository.findByEmployee(idEmployee)) {
            TicketResponse ticketResponse = new TicketResponse(ticket);
            if (attendanceRepository.findByTicket(ticket.getId()).isPresent()) {
                ticketResponse.setIsAttend(true);
            } else {
                ticketResponse.setIsAttend(false);
            }
            tickets.add(ticketResponse);
        }

        return tickets;
    }

    public List<TicketResponse> findByEvent(Long idEvent) {
        List<TicketResponse> tickets = new ArrayList<>();

        for (Ticket ticket : ticketRepository.findByEvent(idEvent)) {
            TicketResponse ticketResponse = new TicketResponse(ticket);
            if (attendanceRepository.findByTicket(ticket.getId()).isPresent()) {
                ticketResponse.setIsAttend(true);
            } else {
                ticketResponse.setIsAttend(false);
            }
            tickets.add(ticketResponse);
        }

        return tickets;
    }

    public List<TicketResponse> findBySchedule(Long idEvent, Long idSchedule) {
        List<TicketResponse> tickets = new ArrayList<>();

        for (Ticket ticket : ticketRepository.findByEvent(idEvent)) {
            TicketResponse ticketResponse = new TicketResponse(ticket);
            if (attendanceRepository.findByTicketAndSchedule(ticket.getId(), idSchedule).isPresent()) {
                ticketResponse.setIsAttend(true);
            } else {
                ticketResponse.setIsAttend(false);
            }
            tickets.add(ticketResponse);
        }

        return tickets;
    }

}
