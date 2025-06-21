/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client_side.event_management.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

/**
 *
 * @author Aldyth
 * @param <T> entity Type
 */
public class CreateHeader<T> {
    public static <T> HttpEntity<T> getHeader() {
        HttpEntity<T> entity = new HttpEntity<>(createHeaders());
        return entity;
    }

    public static <T> HttpEntity<T> entityWithBody(T body) {
        HttpEntity<T> entity = new HttpEntity<>(body, createHeaders());

        return entity;
    }

    private static HttpHeaders createHeaders() {
        return new HttpHeaders() {
            {
                set("Authorization", "Basic " + GetAuthContext.getAuthorization().getCredentials().toString());
            }
        };
    }
}
