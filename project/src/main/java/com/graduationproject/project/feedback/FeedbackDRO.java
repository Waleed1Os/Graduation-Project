package com.graduationproject.project.feedback;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackDRO {
private String complaint;
private FeedbackType type;
private Date whenMade;    
}
