package com.client_side.event_management.services;

import com.client_side.event_management.models.Event;
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
public class EventService {

    private RestTemplate restTemplate;

    @Value("${server.serverUrl}/event")
    private String url;

    @Autowired
    public EventService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseListData<Event> findAllEvent(String title) {
        String param = "";
        if (title!=null) {
            param = "?title="+title;
        }
        return restTemplate
                .exchange(url+param, HttpMethod.GET, null, new ParameterizedTypeReference<ResponseListData<Event>>() {
                }).getBody();
    }

    public ResponseListData<Event> findByStatus(Long status) {
        return restTemplate
                .exchange(url + "/status/" + status, HttpMethod.GET, null, new ParameterizedTypeReference<ResponseListData<Event>>() {
                }).getBody();
    }
    
    public ResponseData<Event> findByIdEvent(Long id) {
        return restTemplate
                .exchange(url + "/" + id, HttpMethod.GET, null, new ParameterizedTypeReference<ResponseData<Event>>() {
                }).getBody();
    }
    
    public ResponseListData<Event> findByType(String type) {
        return restTemplate.exchange(url+"/"+type, HttpMethod.GET, null, 
                new ParameterizedTypeReference<ResponseListData<Event>>() {}).getBody();
    }

    public ResponseMessage<Event> createEvent(Event event) {
        HttpEntity<Event> entity = new HttpEntity<>(event);
        return restTemplate
                .exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseMessage<Event>>() {
                }).getBody();
    }

    public ResponseMessage<Event> updateEvent(Event event, Long id) {
        event.setId(id);
        HttpEntity<Event> entity = new HttpEntity<>(event);
        return restTemplate.exchange(url + "/" + id, HttpMethod.PUT, entity,
                new ParameterizedTypeReference<ResponseMessage<Event>>() {
                }).getBody();
    }

    public ResponseMessage<Event> deleteEvent(Long id) {
        return restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, null,
                new ParameterizedTypeReference<ResponseMessage<Event>>() {
                }).getBody();
    }

}