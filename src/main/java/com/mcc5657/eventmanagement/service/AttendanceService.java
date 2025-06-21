package com.mcc5657.eventmanagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mcc5657.eventmanagement.dto.AttendanceRequest;
import com.mcc5657.eventmanagement.dto.AttendanceResponse;
import com.mcc5657.eventmanagement.model.Attendance;
import com.mcc5657.eventmanagement.model.Event;
import com.mcc5657.eventmanagement.model.Schedule;
import com.mcc5657.eventmanagement.model.Ticket;
import com.mcc5657.eventmanagement.repository.AttendanceRepository;
import com.mcc5657.eventmanagement.repository.EventRepository;
import com.mcc5657.eventmanagement.repository.ScheduleRepository;
import com.mcc5657.eventmanagement.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EventRepository eventEventRepository;
    private final TicketRepository ticketRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository, EventRepository eventEventRepository,
            TicketRepository ticketRepository, ScheduleRepository scheduleRepository) {
        this.attendanceRepository = attendanceRepository;
        this.eventEventRepository = eventEventRepository;
        this.ticketRepository = ticketRepository;
        this.scheduleRepository = scheduleRepository;
    }

    // public List<AttendanceResponse> getParticipantByIdSchedule(Long idSchedule) {
    // List<Attendance> attendances =
    // attendanceRepository.findByIdSchedule(idSchedule);
    // List<AttendanceResponse> datas = new ArrayList<>();
    // attendances.forEach((attendance) -> {
    // datas.add(new AttendanceResponse(attendance));
    // });
    // }

    public AttendanceResponse insertAttendanceByUser(AttendanceRequest request) {
        Ticket ticket = ticketRepository.findById(request.getIdTicket())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Event event = eventEventRepository.findById(ticket.getEvent().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Schedule schedule = null;

        LocalDateTime presentDate = LocalDateTime.now();
        AttendanceResponse response = new AttendanceResponse();
        response.setStarted(false);

        for (Schedule sc : scheduleRepository.findByEvent(event.getId())) {
            if (sc.getEventType().getIsOnline()) {
                if (presentDate.getDayOfMonth() == sc.getStartTime().getDayOfMonth()) {
                    if (presentDate.isAfter(sc.getStartTime().minusMinutes(15))
                            && presentDate.isBefore(sc.getEndTime().plusMinutes(15))) {
                        schedule = sc;
                        response.setStarted(true);
                        response.setLink(schedule.getEventType().getLinkPlatform());
                    }
                }
            }
        }

        if (schedule != null) {
            Attendance attendance = Attendance.builder().ticket(ticket).schedule(schedule).isPresent(true)
                    .presentTime(presentDate).build();

            if (!attendanceRepository.findByTicketAndSchedule(ticket.getId(), schedule.getId()).isPresent()) {
                attendanceRepository.save(attendance);
            }

            return response;
        }

        response.setIdTicket(ticket.getId());
        response.setName(ticket.getEmployee().getName());
        response.setEventTitle(event.getTitle());

        return response;

    }

    public AttendanceResponse insertAttendance(AttendanceRequest request) {
        Ticket ticket = ticketRepository.findById(request.getIdTicket())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Event event = eventEventRepository.findById(ticket.getEvent().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Schedule schedule = null;

        LocalDateTime presentDate = LocalDateTime.now();
        AttendanceResponse response = new AttendanceResponse();
        response.setStarted(false);

        for (Schedule sc : scheduleRepository.findByEvent(event.getId())) {
            System.out.println(sc.getStartTime());
            if (presentDate.getDayOfMonth() == sc.getStartTime().getDayOfMonth()) {
                if (presentDate.isAfter(sc.getStartTime()) && presentDate.isBefore(sc.getEndTime())) {
                    schedule = sc;
                    response.setStarted(true);
                    response.setLink(schedule.getEventType().getLinkPlatform());
                }
            }
        }

        if (schedule != null) {
            Attendance attendance = Attendance.builder().ticket(ticket).schedule(schedule).isPresent(true)
                    .presentTime(presentDate).build();

            if (!attendanceRepository.findByTicketAndSchedule(ticket.getId(), schedule.getId()).isPresent()) {
                attendanceRepository.save(attendance);
            }
            System.out.println("schedule: " + schedule.getId());
            System.out.println("ticket: " + attendance.getTicket().getId());
        }

        System.out.println(response.isStarted());
        response.setIdTicket(ticket.getId());
        response.setName(ticket.getEmployee().getName());
        response.setEventTitle(event.getTitle());

        return response;
    }
}
