/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc5657.eventmanagement.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author kintan semi
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "verification_token")
public class VerificationToken {

    @Id
    @Column(name = "id", length = 5, nullable = false)
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_account")
    private Account account;

    @Column(name = "token", length = 255, nullable = false)
    private String token;

    @Column(name = "expired_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSX")
    private LocalDateTime expiredDate;

    @Column(name = "is_used", nullable = false)
    private boolean isUsed;

}
