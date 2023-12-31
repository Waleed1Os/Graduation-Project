package com.graduationproject.project.inference;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.graduationproject.project.Checkers;
import com.graduationproject.project.Utils;
import com.graduationproject.project.user.User;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InferenceService {
private final InferenceRepository inferenceRepository;    
public List<InferenceDTO> getInferences(Pageable pageable,Principal connectedUser){
final User user = Utils.getConnectedUser(connectedUser);
return inferenceRepository.findByUsertDTO(user, pageable).getContent(); 
}
public InferenceResponse infereAI(String query,Principal connectedUser){
if(!Checkers.checkQuery(query)){
   return null;//Or throw maybe
}   
final User user = Utils.getConnectedUser(connectedUser);
final Inference inference = Inference.builder()
.query(query)
.user(user)
.whenMade(new Date())
.build();
final int saveInferenceId = inferenceRepository.save(inference).getId();
return InferenceResponse.builder()
.id(saveInferenceId)
.response(query).build();//Will replace it with response once it is ready 
}

public void deleteInference(int id,Principal principal) throws AuthException{

final User user = Utils.getConnectedUser(principal);
final Inference inference = inferenceRepository.findById(id).orElseThrow();
//We didn't totally remove it from db as we might need it for training but it is no more related to the user
Utils.unrelateToUser(user, inference);
inferenceRepository.save(inference);
}

public void reportFalseResponse(int id,List<String> incorrectWords,Principal principal) throws AuthException{

   if(incorrectWords.isEmpty()){
      return;
   }
 final Inference inference = inferenceRepository.findById(id).orElseThrow();
 final User user = Utils.getConnectedUser(principal); 
 if(!Utils.isTheOwner(user, inference)){
    throw new AuthException();
 }
inference.setCorrect(false);
inference.setIncorrectWords(String.join(",", incorrectWords));
inferenceRepository.save(inference);
}

}
