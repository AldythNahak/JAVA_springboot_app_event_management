package com.mcc5657.eventmanagement.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "schedule")
public class Schedule implements BasicCrudModel<Schedule, Long> {

    @Id
    @Column(name = "id", length = 5, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Event is required")
    @ManyToOne
    @JoinColumn(name = "id_event", nullable = false)
    private Event event;

    @NotNull(message = "Start Time is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;
    
    @NotNull(message = "End Time is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Size(max = 255, message = "Location should not be greater than 30 characters")
    @Column(name = "location", length = 255, nullable = true)
    private String location;

    @OneToOne(mappedBy = "schedule", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private EventType eventType;

    @OneToMany(mappedBy = "schedule")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Attendance> attendances;
}
