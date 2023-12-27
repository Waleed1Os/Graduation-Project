package com.graduationproject.project.inference;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.graduationproject.project.Utils;
import com.graduationproject.project.user.User;
// import com.graduationproject.project.user.UserRepository;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InferenceService {
private final InferenceRepository inferenceRepository;    
// private final UserRepository userRepository;
public List<Inference> getInferences(Principal connectedUser){
final User user = Utils.getConnectedUser(connectedUser);
return user.getInferences();
}
public int infereAI(String query,Principal connectedUser){
final User user = Utils.getConnectedUser(connectedUser);
final Inference inference = Inference.builder()
.query(query)
.user(user)
.whenMade(new Date())
.build();
final int saveInferenceId = inferenceRepository.save(inference).getId();
return saveInferenceId; 
}

public void deleteInference(int id,Principal principal) throws AuthException{

final User user = Utils.getConnectedUser(principal);
final Inference inference = inferenceRepository.findById(id).orElseThrow();
//We didn't totally remove it from db as we might need it for training but it is no more related to the user
Utils.unrelateToUser(user, inference);
inferenceRepository.save(inference);
}

}
