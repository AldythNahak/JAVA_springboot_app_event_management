/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client_side.event_management.models;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Aldyth
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private Long id;

    @NotEmpty(message = "Title is required")
    @Size(max = 255, message = "Title should not be greater than 255 characters")
    private String title;

    @Size(max = 255, message = "Description should not be greater than 255 characters")
    private String description;

    @Size(max = 30, message = "Contact Person should not be greater than 30 characters")
    private String contactPerson;

    @NotNull(message = "Registration Deadline is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private LocalDateTime registrationDeadline;

    @Max(value = 99999, message = "Quota should not be greater than 5 digit")
    @NotNull(message = "Quota is required")
    private Long quota;

    @NotEmpty(message = "Image is required")
    private String image;

    private Long status;
    
    private Long participantQuantity;

//    @Valid
    private List<SchedulerRequest> schedulers;
}
