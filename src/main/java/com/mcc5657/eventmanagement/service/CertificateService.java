package com.mcc5657.eventmanagement.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mcc5657.eventmanagement.dto.AttendanceRequest;
import com.mcc5657.eventmanagement.dto.CertificateResponse;
import com.mcc5657.eventmanagement.model.Attendance;
import com.mcc5657.eventmanagement.model.Employee;
import com.mcc5657.eventmanagement.model.Event;
import com.mcc5657.eventmanagement.model.Schedule;
import com.mcc5657.eventmanagement.model.Ticket;
import com.mcc5657.eventmanagement.repository.AttendanceRepository;
import com.mcc5657.eventmanagement.repository.ScheduleRepository;
import com.mcc5657.eventmanagement.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CertificateService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    public CertificateResponse getCertificate(AttendanceRequest request) {
        Ticket ticket = ticketRepository.findById(request.getIdTicket())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));
        Event event = ticket.getEvent();
        Employee employee = ticket.getEmployee();

        List<Schedule> schedules = scheduleRepository.findByEvent(event.getId());
        List<Attendance> attendances = new ArrayList<>();

        List<LocalDateTime> startDateTimes = new ArrayList<>();
        List<LocalDateTime> endDateTimes = new ArrayList<>();

        for (Schedule schedule : schedules) {
            startDateTimes.add(schedule.getStartTime());
            endDateTimes.add(schedule.getEndTime());
            Attendance attendance = attendanceRepository.findByTicketAndSchedule(ticket.getId(), schedule.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));
            attendances.add(attendance);
        }

        if (attendances.size() != schedules.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime startDate = Collections.min(startDateTimes);
        LocalDateTime endDate = Collections.max(endDateTimes);

        return new CertificateResponse(employee.getName(), event.getTitle(), dtf.format(startDate),
                dtf.format(endDate));

    }
}
