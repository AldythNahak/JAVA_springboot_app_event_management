/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client_side.event_management.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
public class Employee {

    private Long id;

    @NotEmpty(message = "Name is required")
    @Size(max = 100, message = "Name should not be grater than 100 characters")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email is not valid")
    @Size(max = 30, message = "Email should not be greater than 30 characters")
    private String email;

    @Size(max = 15, message = "Phone number should not be grater than 15 characters")
    private String phoneNumber;
}