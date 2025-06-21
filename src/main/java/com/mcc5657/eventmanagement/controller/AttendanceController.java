package com.mcc5657.eventmanagement.controller;

import com.mcc5657.eventmanagement.dto.AttendanceRequest;
import com.mcc5657.eventmanagement.dto.AttendanceResponse;
import com.mcc5657.eventmanagement.model.response.ResponseData;
import com.mcc5657.eventmanagement.model.response.ResponseListData;
import com.mcc5657.eventmanagement.model.response.ResponseMessage;
import com.mcc5657.eventmanagement.service.AttendanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceService service;

    @Autowired
    public AttendanceController(AttendanceService service) {
        this.service = service;
    }

    // Only for testing
    @GetMapping
    public ResponseData<AttendanceResponse> create(@RequestParam("ticket") String idTicket) {
        return new ResponseData<>(service.insertAttendanceByUser(new AttendanceRequest(idTicket)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseMessage<AttendanceResponse> insertByAdmin(@RequestBody AttendanceRequest request) {
        AttendanceResponse response = service.insertAttendance(request);
        if (response.isStarted()) {
            return new ResponseMessage<>(response, "Your attendance has been recorded");
        } else {
            return new ResponseMessage<>(response, "Failed to record your attendance");
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/user")
    public ResponseData<AttendanceResponse> insertByUser(@RequestBody AttendanceRequest request) {
        return new ResponseData<>(service.insertAttendanceByUser(request));
    }

}
