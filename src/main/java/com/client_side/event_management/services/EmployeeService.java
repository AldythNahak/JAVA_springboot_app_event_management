package com.client_side.event_management.services;

import com.client_side.event_management.models.Employee;
import com.client_side.event_management.models.response.ResponseData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeService {
  private RestTemplate restTemplate;

  @Value("${server.serverUrl}/employee")
  private String url;

  @Autowired
  public EmployeeService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public ResponseData<Employee> findByEmail(String email) {
    return restTemplate.exchange(url + "?email=" + email, HttpMethod.GET, null,
        new ParameterizedTypeReference<ResponseData<Employee>>() {
        }).getBody();
  }
}
