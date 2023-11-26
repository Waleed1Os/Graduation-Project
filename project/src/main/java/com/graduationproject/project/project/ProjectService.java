package com.graduationproject.project.project;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.graduationproject.project.user.User;
import com.graduationproject.project.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
// private final ProjectRepository projectRepository;    
private final UserRepository userRepository;
@PreAuthorize("#id==authentication.principal.id")
public List<Project> getProjects(int id){
final User user = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User not found"));
return user.getProjects();
}


}
