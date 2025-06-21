/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc5657.eventmanagement.service;

import com.mcc5657.eventmanagement.dto.LoginRequest;
import com.mcc5657.eventmanagement.dto.LoginResponse;
import java.time.LocalDateTime;
import java.util.UUID;

import com.mcc5657.eventmanagement.model.Account;
import com.mcc5657.eventmanagement.model.Employee;
import com.mcc5657.eventmanagement.model.UserDetail;
import com.mcc5657.eventmanagement.model.VerificationToken;
import com.mcc5657.eventmanagement.repository.AccountRepository;
import com.mcc5657.eventmanagement.repository.EmployeeRepository;
import com.mcc5657.eventmanagement.repository.RoleRepository;
import com.mcc5657.eventmanagement.repository.VerificationTokenRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author kintan semi
 */
@Service
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailService userDetailService;
    private final EmailSenderService emailSenderService;

    @Autowired
    public AccountService(PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository,
            VerificationTokenRepository verificationTokenRepository, RoleRepository roleRepository,
            AccountRepository accountRepository, AuthenticationManager authenticationManager,
            UserDetailService userDetailService, EmailSenderService emailSenderService) {
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.emailSenderService = emailSenderService;
    }

    public Employee registration(Employee employee) {
        Account account = new Account();

        account.setPassword(encryptPass(UUID.randomUUID().toString()));
        account.setRoles(roleRepository.findByName("user"));
        account.setEmployee(employee);
        employee.setAccount(account);
        
        employeeRepository.save(employee);
        String token = UUID.randomUUID().toString();
        VerificationToken vToken = VerificationToken.builder().account(employee.getAccount()).token(token)
                .expiredDate(LocalDateTime.now().plusDays(1)).isUsed(false).build();
        verificationTokenRepository.save(vToken);

        String link = "http://localhost:8089/auth/create-password?token=" + token;
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", employee.getName());
        model.put("link", link);

        emailSenderService.sendRegistrationAccount(employee.getEmail(), model);

        return employee;
    }

    public Employee changePasswordVerification(String token, String password) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TOKEN NOT FOUND");
        }

        if (LocalDateTime.now().isAfter(verificationToken.getExpiredDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TOKEN HAS EXPIRED");
        }

        if (verificationToken.isUsed()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TOKEN HAS BEEN USED");
        }
        Account account = verificationToken.getAccount();
        account.setPassword(encryptPass(password));
        verificationToken.setUsed(true);
        accountRepository.save(account);
        verificationTokenRepository.deleteById(verificationToken.getId());
        return account.getEmployee();
    }

    public Employee changePassword(String password) {
        System.out.println("masuk service");
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("principal dapet");
        Employee employee = employeeRepository.findByEmail(userDetail.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));
        Account account = accountRepository.findById(employee.getId())
                .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));
        account.setPassword(encryptPass(password));
        accountRepository.save(account);
        System.out.println("password baru kesave");
        return employee;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        Employee employee = employeeRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));
        Account account = accountRepository.findById(employee.getId())
                .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));
        UserDetails userDetails = userDetailService.loadUserByUsername(loginRequest.getEmail());

        List<String> authorities = userDetails.getAuthorities().stream().map(authority -> authority.getAuthority())
                .collect(Collectors.toList());

        return new LoginResponse(account, authorities);
    }

    public Employee forgetPassword(String email) {

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));
        Account account = accountRepository.findById(employee.getId())
                .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));

        String token = UUID.randomUUID().toString();
        Optional<VerificationToken> vToken = verificationTokenRepository.findById(account.getId());
        VerificationToken verificationToken;
        if (vToken.isPresent()) {
            verificationToken = vToken.get();
            verificationToken.setExpiredDate(LocalDateTime.now().plusDays(1));
            verificationToken.setToken(token);
            verificationToken.setUsed(false);
        } else {
            verificationToken = VerificationToken.builder().account(account).token(token)
                    .expiredDate(LocalDateTime.now().plusDays(1)).isUsed(false).build();
        }
        verificationTokenRepository.save(verificationToken);
        String link = "http://localhost:8089/auth/create-password?token=" + token;
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", employee.getName());
        model.put("link", link);
        emailSenderService.sendResetPassword(employee.getEmail(), model);

        return employee;
    }

    private String encryptPass(String password) {
        return passwordEncoder.encode(password);
    }
}
