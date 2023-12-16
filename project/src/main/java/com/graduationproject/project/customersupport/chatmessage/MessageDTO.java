package com.graduationproject.project.customersupport.chatmessage;

import java.util.Date;

import com.graduationproject.project.customersupport.SenderRepresentation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
private int id;
private Date whenSent;
private String msg;
private SenderRepresentation sender;
}
