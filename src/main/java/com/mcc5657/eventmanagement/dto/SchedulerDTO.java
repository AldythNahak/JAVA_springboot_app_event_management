package com.mcc5657.eventmanagement.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mcc5657.eventmanagement.model.EventType;
import com.mcc5657.eventmanagement.model.Schedule;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchedulerDTO {
    
    private Long id;

    @NotNull(message = "Is Online is required")
    private Boolean isOnline;

    @Size(max = 255, message = "Link Platform should not be greater than 255 characters")
    private String linkPlatform;

    @NotNull(message = "Start Time is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private LocalDateTime startTime;
    
    @NotNull(message = "End Time is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private LocalDateTime endTime;

    @Size(max = 255, message = "Location should not be greater than 30 characters")
    private String location;

    private Long idEvent;

    public SchedulerDTO (EventType eventType, Schedule schedule) {
        id = schedule.getId();
        isOnline = eventType.getIsOnline();
        linkPlatform = eventType.getLinkPlatform();
        startTime = schedule.getStartTime();
        endTime = schedule.getEndTime();
        location = schedule.getLocation();
        idEvent = schedule.getEvent().getId();
    }
}
