package com.mcc5657.eventmanagement.model;

import java.util.List;

public class TicketMail {
    private String name;
    private String event;
    private List<Schedule> schedules;
    private String link;

    public TicketMail(String name, String event, List<Schedule> schedules, String link) {
        this.name = name;
        this.event = event;
        this.schedules = schedules;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
