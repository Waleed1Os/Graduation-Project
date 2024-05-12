package com.graduationproject.project.mail;

import java.util.Map;

import jakarta.mail.MessagingException;

public interface Sender {
public void sendEmail(String to, String subject, String body) throws MessagingException;    
public void sendEmailWithTemplate(String to, String subject, String HTMLFileName,Map<String,Object> variables) throws MessagingException;
public void getMessageFromUser(String email,String title,String body) throws MessagingException;

}
