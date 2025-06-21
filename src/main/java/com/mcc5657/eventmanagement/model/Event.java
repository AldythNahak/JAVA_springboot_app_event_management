package com.mcc5657.eventmanagement.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "event")
public class Event implements BasicCrudModel<Event, Long> {

    @Id
    @Column(name = "id", length = 5, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Title is required")
    @Size(max = 255, message = "Title should not be greater than 255 characters")
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Lob
    @Column(name = "description", length = 1023, nullable = true)
    private String description;

    @Size(max = 30, message = "Contact Person should not be greater than 30 characters")
    @Column(name = "contact_person", length = 30, nullable = true)
    private String contactPerson;

    @NotNull(message = "Registration Deadline is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @Column(name = "registration_deadline", nullable = false)
    private LocalDateTime registrationDeadline;

    @Max(value = 99999, message = "Quota should not be greater than 5 digit")
    @NotNull(message = "Quota is required")
    @Column(name = "quota", length = 5, nullable = false)
    private Long quota;

    @Max(value = 99999, message = "Participant Quantity should not be greater than 5 digit")
    @NotNull(message = "Participant Quantity is required")
    @Column(name = "participant_quantity", length = 5, nullable = false)
    private Long participantQuantity;

    @NotNull(message = "Is Delete is required")
    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete;

    @Lob
    @NotNull(message = "Image is required")
    @Column(name = "image", nullable = false)
    private byte[] image;

    @NotNull(message = "Status is required")
    @ManyToOne
    @JoinColumn(name = "id_status", nullable = false)
    private Status status;

    @OneToMany(mappedBy = "event")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Schedule> schedules;

    @OneToMany(mappedBy = "event")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Ticket> tickets;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactPerson() {
        return this.contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public LocalDateTime getRegistrationDeadline() {
        return this.registrationDeadline;
    }

    public void setRegistrationDeadline(LocalDateTime registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public Long getQuota() {
        return this.quota;
    }

    public void setQuota(Long quota) {
        this.quota = quota;
    }

    public Long getParticipantQuantity() {
        return this.participantQuantity;
    }

    public void setParticipantQuantity(Long participantQuantity) {
        this.participantQuantity = participantQuantity;
    }

    public Boolean isIsDelete() {
        return this.isDelete;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Schedule> getSchedules() {
        return this.schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Set<Ticket> getTickets() {
        return this.tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

}
