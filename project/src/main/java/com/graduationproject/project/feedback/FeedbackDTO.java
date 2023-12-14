package com.graduationproject.project.feedback;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
public class FeedbackDTO {
private Integer id;
private String complaint;
private Date whenMade;
private FeedbackType type;  
}
