package com.graduationproject.project.project;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ProjectTDO {
private Integer id;
private String query;
private String response;
private Date whenMade;
@Setter
private boolean correct;    
}
