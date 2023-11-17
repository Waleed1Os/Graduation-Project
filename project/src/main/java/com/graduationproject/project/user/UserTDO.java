package com.graduationproject.project.user;

import java.util.Date;
import java.util.List;

import com.graduationproject.project.project.ProjectTDO;

import lombok.Getter;

@Getter
public class UserTDO {
private Integer id;    
private String firstName,lastName;
private String username;
private String email;
private List<ProjectTDO> projects; 
private Date whenCreated;
}
