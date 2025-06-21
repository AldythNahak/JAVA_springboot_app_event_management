/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc5657.eventmanagement.dto;

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
public class LoginRequest {
    
    private String email;
    private String password;

   
}
