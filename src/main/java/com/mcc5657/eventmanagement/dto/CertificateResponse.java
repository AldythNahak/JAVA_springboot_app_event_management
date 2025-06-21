package com.mcc5657.eventmanagement.dto;

public class CertificateResponse {
    private String name;
    private String eventTitle;
    private String startDate;
    private String endDate;

    public CertificateResponse() {
    }

    public CertificateResponse(String name, String eventTitle, String startDate, String endDate) {
        this.name = name;
        this.eventTitle = eventTitle;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
