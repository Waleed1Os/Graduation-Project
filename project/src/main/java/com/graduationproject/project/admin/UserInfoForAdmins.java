package com.graduationproject.project.admin;

import java.util.Date;
import java.util.List;

import com.graduationproject.project.customersupport.SupportSession;
import com.graduationproject.project.feedback.FeedbackDTO;
import com.graduationproject.project.user.Role;


public record UserInfoForAdmins(String userName,
 String email,
 String firstName,
 String lastName,
 boolean notBanned,
 Role role,
 Date premium,
 List<FeedbackDTO> feedbacks,
 List<SupportSession> supportSessions) {
 
}
