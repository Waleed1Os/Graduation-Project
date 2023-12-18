package com.graduationproject.project.inference;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.graduationproject.project.user.User;
import com.graduationproject.project.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InferenceService {
private final InferenceRepository inferenceRepository;    
private final UserRepository userRepository;
public List<Inference> getInferences(int id){
final User user = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User not found"));
return user.getInferences();
}
public int infereAI(String query,Principal connectedUser){
final User user = (User)((UsernamePasswordAuthenticationToken)connectedUser).getPrincipal();
final Inference inference = Inference.builder()
.query(query)
.user(user)
.whenMade(new Date())
.build();
final int saveInferenceId = inferenceRepository.save(inference).getId();
return saveInferenceId; 
}

}
