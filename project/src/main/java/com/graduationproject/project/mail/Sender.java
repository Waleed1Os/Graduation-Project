package com.graduationproject.project.mail;


public interface Sender {


public void sendEmail(String to,String subject,String body,boolean isHTML);
public void getMessageFromUser(String email,String body);


}
