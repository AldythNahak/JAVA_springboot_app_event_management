package com.client_side.event_management.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateResponse {

    private String name;
    private String eventTitle;
    private String startDate;
    private String endDate;
    private String location;

}
