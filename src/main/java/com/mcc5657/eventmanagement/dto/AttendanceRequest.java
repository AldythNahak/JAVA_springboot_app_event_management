package com.mcc5657.eventmanagement.dto;

public class AttendanceRequest {
    private String idTicket;

    public AttendanceRequest() {
    }

    public AttendanceRequest(String idTicket) {
        this.idTicket = idTicket;
    }

    public String getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(String idTicket) {
        this.idTicket = idTicket;
    }

}
