package com.graduationproject.project.admin;

import java.util.Date;
import java.util.List;

import com.graduationproject.project.customersupport.SupportSession;
import com.graduationproject.project.feedback.FeedbackDTO;
import com.graduationproject.project.user.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoForAdmins {
private String userName;
private String email;
private String firstName;
private String lastName;
private boolean notBanned;
private Role role;
private Date premium;
private List<FeedbackDTO> feedbacks;
private List<SupportSession> supportSessions;    
}
