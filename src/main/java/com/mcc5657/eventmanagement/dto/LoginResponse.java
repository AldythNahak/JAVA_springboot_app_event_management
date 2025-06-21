/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc5657.eventmanagement.dto;

import com.mcc5657.eventmanagement.model.Account;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author kintan semi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    
    private Long id;
    
    private String name;
    
    private String email;
    
    private List<String> authorities;

    public LoginResponse(Account account, List<String> authorities) {
        this.id = account.getId();
        this.name = account.getEmployee().getName();
        this.email = account.getEmployee().getEmail();
        this.authorities = authorities;
    }

}