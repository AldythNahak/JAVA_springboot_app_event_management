package com.mcc5657.eventmanagement.dto;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mcc5657.eventmanagement.model.Event;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    private Long id;

    @NotEmpty(message = "Title is required")
    @Size(max = 255, message = "Title should not be greater than 255 characters")
    private String title;

    @Size(max = 1023, message = "Description should not be greater than 1023 characters")
    private String description;

    @Size(max = 30, message = "Contact Person should not be greater than 30 characters")
    private String contactPerson;

    @NotNull(message = "Registration Deadline is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private LocalDateTime registrationDeadline;

    @Max(value = 99999, message = "Quota should not be greater than 5 digit")
    @NotNull(message = "Quota is required")
    private Long quota;

    private Long participantQuantity;

    private Boolean isDelete;

    @NotEmpty(message = "Image is required")
    private String image;

    private Long status;

    @Valid
    @NotNull(message = "Schedulers is required")
    private List<SchedulerDTO> schedulers;

    public EventDTO(Event event, List<SchedulerDTO> schedulers) {
        this.id = event.getId();
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.contactPerson = event.getContactPerson();
        this.registrationDeadline = event.getRegistrationDeadline();
        this.quota = event.getQuota();
        this.participantQuantity = event.getParticipantQuantity();
        this.isDelete = event.getIsDelete();
        this.image = Base64.getEncoder().encodeToString(event.getImage());
        this.status = event.getStatus().getId();
        this.schedulers = schedulers;
    }

}
