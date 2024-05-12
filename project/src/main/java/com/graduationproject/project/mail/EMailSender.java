package com.graduationproject.project.mail;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EMailSender implements Sender{
@Value("${spring.mail.username}")  
private String serverEmail;
private final JavaMailSender javaMailSender;
private final TemplateEngine templateEngine;
@Autowired
public EMailSender(JavaMailSender javaMailSender,TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
       final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setCacheable(false);
        this.templateEngine = templateEngine;
        // this.templateEngine.addTemplateResolver(templateResolver);
        this.templateEngine.setTemplateResolver(templateResolver);
    }
    
    @Override
    public void sendEmailWithTemplate(String to, String subject, String HTMLFileName,Map<String,Object> variables) throws MessagingException {
     final MimeMessage message = javaMailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(message,StandardCharsets.UTF_8.name());
        helper.setFrom(serverEmail);
        helper.setTo(to);
        helper.setText(changeVariablesToValues(HTMLFileName,variables),true);
        helper.setSubject(subject);
        javaMailSender.send(message);
    
        }
    
    private String changeVariablesToValues(String HTMLFileName,Map<String,Object> variables) {
       final Context context = new Context();
        context.setVariables(variables);
       return templateEngine.process(HTMLFileName,context);
    }
    
    @Override
    public void getMessageFromUser(String email,String title,String body) throws MessagingException {
        final SimpleMailMessage message= new SimpleMailMessage();
         message.setFrom(serverEmail);
         message.setTo(serverEmail);
         message.setSubject(title);
         message.setText(body);
         javaMailSender.send(message);

    }

    @Override
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
    
}
