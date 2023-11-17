package com.graduationproject.project.admin;


public interface Admin {

    
    public void banUserByEmail(int adminId,String email);
    public void banUserByUsername(int adminId,String username);
    public void replyToFeedbck(String response,int feedbackId,int adminId);
}
