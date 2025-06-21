package com.client_side.event_management.services;

import com.client_side.event_management.models.AttendanceRequest;
import com.client_side.event_management.models.AttendanceResponse;
import com.client_side.event_management.models.response.ResponseData;
import com.client_side.event_management.models.response.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AttendanceService {

    private RestTemplate restTemplate;

    @Value("${server.serverUrl}/attendance")
    private String url;

    @Autowired
    public AttendanceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseData<AttendanceResponse> create(AttendanceRequest attendance) {
        HttpEntity<AttendanceRequest> entity = new HttpEntity<>(attendance);
        return restTemplate.exchange(url + "/user", HttpMethod.POST, entity,
                new ParameterizedTypeReference<ResponseData<AttendanceResponse>>() {
                }).getBody();
    }

    public ResponseMessage<AttendanceResponse> createByAdmin(AttendanceRequest attendance) {
        HttpEntity<AttendanceRequest> entity = new HttpEntity<>(attendance);
        return restTemplate.exchange(url, HttpMethod.POST, entity,
                new ParameterizedTypeReference<ResponseMessage<AttendanceResponse>>() {
                }).getBody();
    }
}
