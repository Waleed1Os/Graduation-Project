package com.graduationproject.project.admin;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.graduationproject.project.project.Project;
import com.graduationproject.project.project.ProjectRepository;
import com.graduationproject.project.project.ProjectTDO;
import com.graduationproject.project.user.User;
import com.graduationproject.project.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminService implements Admin{
private final UserRepository userRepository;
private final ProjectRepository projectRepository;

@Override
public void banUserByEmail(int adminId, String email) {
       User user = userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
   user.setAccountNonLocked(false);
   User admin= userRepository.findById(adminId).orElseThrow(()->new UsernameNotFoundException("User not found"));
   admin.getUsersBanned().add(user);
   userRepository.saveAll(List.of(user,admin));
}

@Override
public void banUserByUsername(int adminId, String username) {
    User user = userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
   user.setAccountNonLocked(false);
   User admin= userRepository.findById(adminId).orElseThrow(()->new UsernameNotFoundException("User not found"));
   admin.getUsersBanned().add(user);
   userRepository.saveAll(List.of(user,admin));
}

@Override
public void replyToFeedbck(String response, int feedbackId, int adminId) {
}

public ProjectTDO getReportedResponses(){
List<Project> projects=projectRepository.findByCorrect(false);
ModelMapper mapper=new ModelMapper();
return mapper.map(projects, ProjectTDO.class);
}

    
    

}
