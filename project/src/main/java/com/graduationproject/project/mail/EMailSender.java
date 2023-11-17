package com.graduationproject.project.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EMailSender implements Sender{
  @Value("${spring.mail.username}")  
private String serverEmail;
private final JavaMailSender javaMailSender;
    @Override
    public void sendEmail(String to, String title, String body,boolean isHTML) {
         SimpleMailMessage message= new SimpleMailMessage();
         message.setFrom(serverEmail);
         message.setTo(to);
         message.setSubject(title);
         message.setText(body);
         javaMailSender.send(message);
    }
    
}
