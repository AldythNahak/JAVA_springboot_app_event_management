package com.client_side.event_management.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    
    private String id;
    private String name;
    private LocalDateTime registrationDate;
    private String eventTitle;
    private String contactPerson;
    private Long idEvent;
    private Boolean isAttend;

}
