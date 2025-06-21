package com.mcc5657.eventmanagement.dto;

import com.mcc5657.eventmanagement.model.Attendance;

public class AttendanceResponse {
    private String idTicket;
    private String name;
    private String eventTitle;
    private String link;
    private boolean isStarted;

    public AttendanceResponse() {
    }

    public AttendanceResponse(Attendance attendance, String message, String link, boolean isStarted) {
        idTicket = attendance.getTicket().getId();
        name = attendance.getTicket().getEmployee().getName();
        eventTitle = attendance.getTicket().getEvent().getTitle();
        this.link = link;
        this.isStarted = isStarted;
    }

    public String getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(String idTicket) {
        this.idTicket = idTicket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

}
