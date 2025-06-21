/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client_side.event_management.models;

import java.time.LocalDateTime;
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
public class SchedulerRequest {
    
    private Long id;
    
    // @NotNull(message = "Is Online is required")
    private Boolean isOnline;

    // @Size(max = 255, message = "Link Platform should not be greater than 255 characters")
    private String linkPlatform;

    // @NotNull(message = "Start Time is required")
    // @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private LocalDateTime startTime;
    
    // @NotNull(message = "End Time is required")
    // @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private LocalDateTime endTime;

    // @Size(max = 255, message = "Location should not be greater than 30 characters")
    private String location;

    private Long idEvent;
}
