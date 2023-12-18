package com.graduationproject.project.admin;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.graduationproject.project.inference.InferenceDTO;

public interface Admin {

    public void banOrUnBanUserByUsername(BanRequest banRequest,Principal connectedUser);
    public UserInfoForAdmins getUserInformationByUsername(String username);
    public UserInfoForAdmins getUserInformationByEmail(String email);
    public void replyToFeedbck(MessageRequest message,Principal connectedUser);
    public List<InferenceDTO> getReportedResponses(Pageable pageable);
}
