/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client_side.event_management.config;

import com.client_side.event_management.utils.GetAuthContext;
import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 *
 * @author Aldyth
 */
public class RestTemplateRequestHeaderInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        if (!request.getURI().getPath().equals("/api/auth/login")
                && !request.getURI().getPath().equals("/api/auth/create-password")
                && !request.getURI().getPath().equals("/api/auth/forget-password")) {
            request.getHeaders().add("Authorization", "Basic " + GetAuthContext
                    .getAuthorization()
                    .getCredentials().toString());
        }
    
        ClientHttpResponse response = execution.execute(request, body);

        return response;
    }
}