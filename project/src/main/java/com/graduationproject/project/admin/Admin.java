package com.graduationproject.project.admin;


public interface Admin {

    
    public void banOrUnbanUserByEmail(int adminId,String email,String reason);
    public void banOrUnBanUserByUsername(int adminId,String username,String reason);
    public void replyToFeedbck(String response,int feedbackId,int adminId);
}
