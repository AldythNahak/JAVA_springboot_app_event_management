package com.mcc5657.eventmanagement.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.mcc5657.eventmanagement.model.Schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    private void sendEmail(String to, String subject, Map<String, Object> model, String template) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        Context context = new Context();
        context.setVariables(model);

        String html = templateEngine.process(template, context);

        try {
            helper.setFrom("testmcc5657@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
        } catch (MessagingException e) {
            throw new IllegalStateException("failed to send email");
        }

        mailSender.send(message);

    }

    public void sendTicketMail(String to, Map<String, Object> model) {
        String template = "ticket-mail";
        String subject = "[REGISTRATION] Successfully register an event ticket";
        sendEmail(to, subject, model, template);
    }

    public void sendRegistrationAccount(String to, Map<String, Object> model) {
        String template = "verification-email";
        String subject = "[NEW ACCOUNT] Successfully register an account";
        sendEmail(to, subject, model, template);
    }

    public void sendResetPassword(String to, Map<String, Object> model) {
        String template = "forgot-password";
        String subject = "[RESET PASSWORD] Request for reset password";
        sendEmail(to, subject, model, template);
    }

}
