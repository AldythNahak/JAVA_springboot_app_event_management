package com.mcc5657.eventmanagement.controller;

import com.mcc5657.eventmanagement.dto.AttendanceRequest;
import com.mcc5657.eventmanagement.dto.CertificateResponse;
import com.mcc5657.eventmanagement.model.response.ResponseData;
import com.mcc5657.eventmanagement.service.CertificateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping
    public ResponseData<CertificateResponse> getCertificate(@RequestBody AttendanceRequest request) {
        return new ResponseData<>(certificateService.getCertificate(request));
    }

}
