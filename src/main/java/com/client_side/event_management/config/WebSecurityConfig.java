/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client_side.event_management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author Aldyth
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/css/**", "/js/**", "/image/**", "/resources/**").permitAll()
                    .antMatchers("/auth/login").permitAll()
                    .antMatchers("/auth/create-password").permitAll()
                    .antMatchers("/ajax/auth/create-password").permitAll()
                    .antMatchers("/ajax/auth/forget-password").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/auth/login")
                    .loginProcessingUrl("/auth/login")
                    .failureForwardUrl("/auth/login?error=true").permitAll()
                .and()
                    .logout()
                    .logoutUrl("/auth/logout")
                    .logoutSuccessUrl("/auth/login?logout=true");
    }
}