package com.graduationproject.project.admin;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BanDTO {
private int id;    
private String clientUsername;
private String reason;
private Date whenBanned;    
}
