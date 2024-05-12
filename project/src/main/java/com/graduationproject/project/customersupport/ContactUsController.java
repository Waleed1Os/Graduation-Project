package com.graduationproject.project.customersupport;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graduationproject.project.mail.EMailSender;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("contact-us")
@RequiredArgsConstructor
public class ContactUsController {
private final EMailSender eMailSender;    

@PostMapping("email")
public void sendEmail(@RequestBody ContactUsRequest request) throws MessagingException {
    System.out.println(request);
eMailSender.getMessageFromUser(request.email(), "ISSUE", request.issue());
System.out.println("done");

}

}
