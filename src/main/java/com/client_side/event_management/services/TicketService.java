package com.client_side.event_management.services;

import com.client_side.event_management.models.TicketRequest;
import com.client_side.event_management.models.TicketResponse;
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
public class TicketService {

    private RestTemplate restTemplate;

    @Value("${server.serverUrl}/ticket")
    private String url;

    @Autowired
    public TicketService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseListData<TicketResponse> findByEvent(Long idEvent) {
        return restTemplate.exchange(url + "?event={idEvent}", HttpMethod.GET, null,
                new ParameterizedTypeReference<ResponseListData<TicketResponse>>() {
                }, idEvent).getBody();
    }

    public ResponseListData<TicketResponse> findBySchedule(Long idEvent, Long idSchedule) {
        return restTemplate.exchange(url + "?event= " + idEvent + "&schedule=" + idSchedule, HttpMethod.GET, null,
                new ParameterizedTypeReference<ResponseListData<TicketResponse>>() {
                }, idSchedule).getBody();
    }

    public ResponseListData<TicketResponse> findByEmployee(Long idEmployee) {
        return restTemplate.exchange(url + "/myticket?employee={idEmployee}", HttpMethod.GET, null,
                new ParameterizedTypeReference<ResponseListData<TicketResponse>>() {
                }, idEmployee).getBody();
    }

    public ResponseMessage<TicketResponse> create(TicketRequest ticket) {
        HttpEntity<TicketRequest> entity = new HttpEntity<>(ticket);
        return restTemplate.exchange(url, HttpMethod.POST, entity,
                new ParameterizedTypeReference<ResponseMessage<TicketResponse>>() {
                }).getBody();
    }

}
