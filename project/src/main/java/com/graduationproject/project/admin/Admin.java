package com.graduationproject.project.admin;


public interface Admin {

    public void banOrUnBanUserByUsername(BanRequest banRequest);
    public void replyToFeedbck(MessageRequest message);
}
