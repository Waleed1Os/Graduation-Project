package com.graduationproject.project.inference;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.graduationproject.project.user.User;
import com.graduationproject.project.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InferenceService {
// private final ProjectRepository projectRepository;    
private final UserRepository userRepository;
public List<Inference> getInferences(int id){
final User user = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User not found"));
return user.getInferences();
}


}
