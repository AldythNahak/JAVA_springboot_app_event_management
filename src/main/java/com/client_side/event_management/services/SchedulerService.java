package com.client_side.event_management.services;

import com.client_side.event_management.models.SchedulerRequest;
import com.client_side.event_management.models.response.ResponseData;
import com.client_side.event_management.models.response.ResponseListData;
import com.client_side.event_management.models.response.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SchedulerService {

    private RestTemplate restTemplate;

    @Value("${server.serverUrl}/scheduler")
    private String url;

    @Autowired
    public SchedulerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseListData<SchedulerRequest> findAllScheduler() {
        return restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<ResponseListData<SchedulerRequest>>() {
                }).getBody();
    }

    public ResponseListData<SchedulerRequest> findAllSchedulerByEvent(Long idEvent) {
        return restTemplate.exchange(url + "/event/" + idEvent, HttpMethod.GET, null,
                new ParameterizedTypeReference<ResponseListData<SchedulerRequest>>() {
                }).getBody();
    }

    public ResponseData<SchedulerRequest> findByIdScheduler(Long id) {
        return restTemplate.exchange(url + "/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<ResponseData<SchedulerRequest>>() {
                }).getBody();
    }

    public ResponseListData<Long> findSchedulePerMonth() {
        return restTemplate.exchange(url + "/year", HttpMethod.GET, null,
                new ParameterizedTypeReference<ResponseListData<Long>>() {
                }).getBody();
    }

    public ResponseMessage<SchedulerRequest> createSchedule(SchedulerRequest scheduler) {
        HttpEntity<SchedulerRequest> entity = new HttpEntity<>(scheduler);
        return restTemplate.exchange(url, HttpMethod.POST, entity,
                new ParameterizedTypeReference<ResponseMessage<SchedulerRequest>>() {
                }).getBody();
    }

    public ResponseMessage<SchedulerRequest> updateSchedule(SchedulerRequest scheduler, Long id) {
        scheduler.setId(id);
        HttpEntity<SchedulerRequest> entity = new HttpEntity<>(scheduler);
        return restTemplate.exchange(url + "/" + id, HttpMethod.PUT, entity,
                new ParameterizedTypeReference<ResponseMessage<SchedulerRequest>>() {
                }).getBody();
    }

    public ResponseMessage<SchedulerRequest> deleteSchedule(Long id) {
        return restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, null,
                new ParameterizedTypeReference<ResponseMessage<SchedulerRequest>>() {
                }).getBody();
    }

}
