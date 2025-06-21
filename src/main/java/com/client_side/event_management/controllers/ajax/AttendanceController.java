/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client_side.event_management.controllers.ajax;

import com.client_side.event_management.models.AttendanceRequest;
import com.client_side.event_management.models.AttendanceResponse;
import com.client_side.event_management.models.response.ResponseData;
import com.client_side.event_management.services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Aldyth
 */
@Controller
@RequestMapping("/ajax/attendance")
public class AttendanceController {

    private AttendanceService AttendanceService;

    @Autowired
    public AttendanceController(AttendanceService AttendanceService) {
        this.AttendanceService = AttendanceService;
    }

    @PostMapping
    public @ResponseBody
    ResponseData<AttendanceResponse> create(@RequestBody AttendanceRequest attendance) {
        return AttendanceService.create(attendance);
    }

}
