package com.mcc5657.eventmanagement.dto;

import java.time.LocalDateTime;

import com.mcc5657.eventmanagement.model.Ticket;

public class TicketResponse {
    private String id;
    private String name;
    private LocalDateTime registrationDate;
    private String eventTitle;
    private String contactPerson;
    private Long idEvent;
    private Boolean isAttend;

    public TicketResponse(Ticket ticket) {
        id = ticket.getId();
        name = ticket.getEmployee().getName();
        registrationDate = ticket.getRegistrationDate();
        eventTitle = ticket.getEvent().getTitle();
        contactPerson = ticket.getEvent().getContactPerson();
        idEvent = ticket.getEvent().getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Long getIdEvent() {
        return this.idEvent;
    }

    public void setIdEvent(Long idEvent) {
        this.idEvent = idEvent;
    }

    public Boolean isIsAttend() {
        return this.isAttend;
    }

    public Boolean getIsAttend() {
        return this.isAttend;
    }

    public void setIsAttend(Boolean isAttend) {
        this.isAttend = isAttend;
    }

}
