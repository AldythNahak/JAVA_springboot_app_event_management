package com.client_side.event_management.services;

import com.client_side.event_management.models.CertificateRequest;
import com.client_side.event_management.models.CertificateResponse;
import com.client_side.event_management.models.response.ResponseData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CertificateService {

    private RestTemplate restTemplate;

    @Value("${server.serverUrl}/certificate")
    private String url;

    @Autowired
    public CertificateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseData<CertificateResponse> create(CertificateRequest request) {
        HttpEntity<CertificateRequest> entity = new HttpEntity<>(request);
        return restTemplate.exchange(url, HttpMethod.POST, entity,
                new ParameterizedTypeReference<ResponseData<CertificateResponse>>() {
                }).getBody();
    }

}
