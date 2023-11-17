package com.graduationproject.project.customersupport.chatmessage;

import java.util.Date;

import com.graduationproject.project.customersupport.SupportSession;
import com.graduationproject.project.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "session_messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
private String message;
private Date whenSent;
private User user;
@ManyToOne(fetch = FetchType.LAZY,targetEntity = SupportSession.class)
private SupportSession report;
}
