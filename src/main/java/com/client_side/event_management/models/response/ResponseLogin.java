/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client_side.event_management.models.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Aldyth
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseLogin {
    private Long id;
    private String name;
    private String email;
    private List<String> authorities;
}