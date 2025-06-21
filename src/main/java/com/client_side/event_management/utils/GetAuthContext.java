/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client_side.event_management.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Aldyth
 */
public class GetAuthContext {
    public static void setAuthorization(Authentication auth) {
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
    
    public static Authentication getAuthorization() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
