package com.mcc5657.eventmanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "event_type")
public class EventType implements BasicCrudModel<EventType, Long> {
    
    @Id
    @Column(name = "id_schedule", length = 5, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull(message = "Is Online is required")
    @Column(name = "is_online", nullable = false)
    private Boolean isOnline;

    @Size(max = 255, message = "Link Platform should not be greater than 255 characters")
    @Column(name = "link_platform", nullable = true)
    private String linkPlatform;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_schedule")
    private Schedule schedule;
}
