package com.graduationproject.project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;

import com.graduationproject.project.mail.EMailSender;
import com.graduationproject.project.tfa.TfaService;

import dev.samstevens.totp.exceptions.CodeGenerationException;
import jakarta.mail.MessagingException;


@SuppressWarnings("unused")

public class TestingJava {
public static void main(String[] args) throws CodeGenerationException, MessagingException {
testTfa();
}


// It only succeded when i choosed SHA1
private static final void testTfa() throws CodeGenerationException, MessagingException {
// TfaService tfaService = new TfaService();
// String secret= tfaService.generateNewSecret();
// System.out.println(tfaService.showCode(secret));
// Scanner in = new Scanner(System.in);
// String input = in.nextLine();
// while(tfaService.isOtpNotValid(secret, input)){
//     input=in.nextLine();
// }
// in.close();
// System.out.println(true);
final JavaMailSender javaMailSender = new JavaMailSenderImpl();
final TemplateEngine templateEngine = new TemplateEngine();
final EMailSender eMailSender = new EMailSender(javaMailSender,templateEngine);
final TfaService tfaService = new TfaService(eMailSender);
System.out.println(tfaService.emailOTP(tfaService.generateNewSecret()));
}


}



