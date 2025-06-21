package com.client_side.event_management.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponse {
    
    private String idTicket;
    private String name;
    private String eventTitle;
    private String link;
    private boolean started;

}
