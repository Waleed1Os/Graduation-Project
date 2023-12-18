package com.graduationproject.project.mail;

import org.springframework.beans.factory.annotation.Value;
// import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EMailSender implements Sender{
  @Value("${spring.mail.username}")  
private String serverEmail;
private final JavaMailSender javaMailSender;
    @Override
    public void sendEmail(String to, String subject, String body,boolean isHTML) {
     final MimeMessage message = javaMailSender.createMimeMessage();
      try {
        final MimeMessageHelper helper = new MimeMessageHelper(message, isHTML);
        helper.setFrom(serverEmail);
        helper.setTo(to);
        helper.setText(body);
        helper.setSubject(subject);
        javaMailSender.send(message);
    } catch (MessagingException e) {
        e.printStackTrace();
    }
        //  SimpleMailMessage message= new SimpleMailMessage();
        //  message.setFrom(serverEmail);
        //  message.setTo(to);
        //  message.setSubject(title);
        //  message.setText(body);
        //  javaMailSender.send(message);
    }
    @Override
    public void getMessageFromUser(String email, String body) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setSubject(email);
            helper.setText(body);
            helper.setFrom(serverEmail);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
    
}
