/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client_side.event_management.services;

import com.client_side.event_management.config.RestTemplateConfig;
import com.client_side.event_management.models.Employee;
import com.client_side.event_management.models.LoginRequest;
import com.client_side.event_management.models.response.ResponseLogin;
import com.client_side.event_management.models.response.ResponseMessage;
import com.client_side.event_management.utils.GetAuthContext;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Aldyth
 */
@Service
public class LoginService {

    private final RestTemplate restTemplate;
    private final RestTemplateConfig restTemplateConfig;

    @Value("${server.serverUrl}/auth")
    private String url;

    @Autowired
    public LoginService(RestTemplate restTemplate, RestTemplateConfig restTemplateConfig) {
        this.restTemplate = restTemplate;
        this.restTemplateConfig = restTemplateConfig;
    }

    public boolean login(LoginRequest request) {
        try {
            HttpEntity<LoginRequest> entity = new HttpEntity<>(request);
            ResponseLogin response = restTemplate.exchange(url + "/login", HttpMethod.POST, entity,
                    new ParameterizedTypeReference<ResponseMessage<ResponseLogin>>() {
                    }).getBody().getData();

            if (response != null) {
                setAuthorization(request, response);
                return true;
            }

            return false;
        } catch (RestClientException e) {
            return false;
        }

    }

    private void setAuthorization(LoginRequest req, ResponseLogin res) {

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(req.getEmail(),
                restTemplateConfig.createCredential(req), getAuthorities(res));

        GetAuthContext.setAuthorization(auth);
    }

    private Collection<GrantedAuthority> getAuthorities(ResponseLogin res) {
        return res.getAuthorities().stream().map(auth -> new SimpleGrantedAuthority(auth)).collect(Collectors.toList());
    }

    public void logout() {
        // restTemplate
        // .exchange(url+"/logout", HttpMethod.GET, null,
        // new ParameterizedTypeReference<ResponseMessage<ResponseLogin>>() {
        // });

        GetAuthContext.setAuthorization(null);
    }

    public ResponseMessage<Employee> register(Employee employee) {
        HttpEntity<Employee> entity = new HttpEntity<>(employee);
        return restTemplate
                .exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseMessage<Employee>>() {
                }).getBody();
    }

    public ResponseMessage<Employee> forgetPassword(Map<String, String> request) {
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request);
        return restTemplate.exchange(url + "/forget-password", HttpMethod.POST, entity,
                new ParameterizedTypeReference<ResponseMessage<Employee>>() {
                }).getBody();
    }

    public ResponseMessage<Employee> createPassword(Map<String, String> request) {
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request);
        return restTemplate.exchange(url + "/create-password", HttpMethod.POST, entity,
                new ParameterizedTypeReference<ResponseMessage<Employee>>() {
                }).getBody();
    }

    public ResponseMessage<Employee> changePassword(Map<String, String> request) {
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request);
        return restTemplate.exchange(url + "/change-password", HttpMethod.POST, entity,
                new ParameterizedTypeReference<ResponseMessage<Employee>>() {
                }).getBody();
    }
}