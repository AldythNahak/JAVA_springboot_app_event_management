package com.client_side.event_management.controllers;

import com.client_side.event_management.models.AttendanceRequest;
import com.client_side.event_management.models.AttendanceResponse;
import com.client_side.event_management.services.AttendanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {
  public final AttendanceService attendanceService;

  @Autowired
  public AttendanceController(AttendanceService attendanceService) {
    this.attendanceService = attendanceService;
  }

  @GetMapping
  public String attendAnEvent(@RequestParam("ticket") String ticket) {
    String roomUrl;
    roomUrl = "attendance/event-not-started-yet";
    AttendanceResponse response = attendanceService.create(new AttendanceRequest(ticket)).getData();
    if (response.isStarted()) {
      roomUrl = response.getLink();
    }
    return "redirect:" + roomUrl;
  }

  @GetMapping("/event-not-started-yet")
  public String eventNotStartedYet() {
    return "user/event-not-started-yet";
  }

}
