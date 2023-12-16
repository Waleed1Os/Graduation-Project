package com.graduationproject.project.customersupport;

import java.util.Date;
import java.util.List;

import com.graduationproject.project.customersupport.chatmessage.MessageDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupportSessionDTO {
private int id;    
private String usernmame;
private Date whenMade;
private boolean closed;
private List<MessageDTO> messages;    
}
