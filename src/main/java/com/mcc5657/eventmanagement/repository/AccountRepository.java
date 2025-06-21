/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc5657.eventmanagement.repository;

import com.mcc5657.eventmanagement.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kintan semi
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

}
