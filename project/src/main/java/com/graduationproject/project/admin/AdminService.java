package com.graduationproject.project.admin;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.graduationproject.project.Utils;
import com.graduationproject.project.customersupport.SupportSession;
import com.graduationproject.project.customersupport.SupportSessionRepository;
import com.graduationproject.project.customersupport.chatmessage.Message;
import com.graduationproject.project.inference.Inference;
import com.graduationproject.project.inference.InferenceRepository;
import com.graduationproject.project.inference.InferenceDTO;
import com.graduationproject.project.user.BannedUser;
import com.graduationproject.project.user.User;
import com.graduationproject.project.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminService implements Admin
 {
private final UserRepository userRepository;
private final InferenceRepository inferenceRepository;
private final SupportSessionRepository sessionRepository;
private final ModelMapper modelMapper;
@Override
public void banOrUnBanUserByUsername(BanRequest banRequest,Principal connectedUser) {
   final User admin = Utils.getConnectedUser(connectedUser);
   final User client = userRepository.findByUsername(banRequest.getClientUsername()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
   final BannedUser bannedUser = BannedUser.builder()
   .admin(admin)
   .whenBanned(new Date())
   .client(client)
   .reason(banRequest.getReason())
   .build();
   admin.addBanRequest(bannedUser);
   client.setNotBanned(!client.isNotBanned());
   userRepository.saveAll(List.of(admin,client));
   //TODO: Send email message to client when unbanned
}


@Override
// @Cacheable("sessions")
public void replyToFeedbck(MessageRequest messageRequest,Principal connectedUser) {
final SupportSession session = sessionRepository.findById(messageRequest.getSessionId()).orElseThrow();
final User admin = Utils.getConnectedUser(connectedUser);
final Message message = Message.builder()
.message(messageRequest.getMessage())
.whenSent(new Date())
.session(session)
.user(admin)
.build();
session.addMessage(message);
sessionRepository.save(session);
}

@Override
public List<InferenceDTO> getReportedResponses(Pageable pageable){
   Page<Inference> page = inferenceRepository.findByCorrect(false, pageable);
   return page.getContent().stream()
              .map(inference -> modelMapper.map(inference, InferenceDTO.class))
              .collect(Collectors.toList());
}


@Override
public UserInfoForAdmins getUserInformationByUsername(String username) {
   final User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
   return modelMapper.map(user,UserInfoForAdmins.class);
}


@Override
public UserInfoForAdmins getUserInformationByEmail(String email) {
  final User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
   return modelMapper.map(user,UserInfoForAdmins.class);
   

}

    
    

}
