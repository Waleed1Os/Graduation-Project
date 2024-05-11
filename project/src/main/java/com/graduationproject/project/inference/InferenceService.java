package com.graduationproject.project.inference;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
// import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// import com.graduationproject.project.Checkers;
import com.graduationproject.project.Utils;
import com.graduationproject.project.user.User;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InferenceService {
private final InferenceRepository inferenceRepository;   
 private final ModelMapper modelMapper;
public List<InferenceDTO> getInferences(
   // Pageable pageable,
   Principal connectedUser){
final User user = Utils.getConnectedUser(connectedUser);
// return inferenceRepository.findByUsertDTO(user
// // ,pageable
// );
final List<InferenceDTO> list = inferenceRepository.findByUser(user).stream().map(inference -> modelMapper.map(inference,InferenceDTO.class)).collect(Collectors.toList());
// .getContent(); 

return list;
}


public InferenceResponse infereAI(InferenceRequest inferenceRequest,Principal connectedUser){
// if(!Checkers.checkQuery(query)){
//    return null;//Or throw maybe
// }   
// RestTemplate restTemplate = new RestTemplate();
//         HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.TEXT_PLAIN);
//         HttpEntity<String> request = new HttpEntity<>(query, headers);
//         ResponseEntity<String> response = restTemplate.exchange("http://127.0.0.1:5000/post_example", HttpMethod.POST, request, String.class);
//         String responseData = response.getBody();
final User user = Utils.getConnectedUser(connectedUser);
final Inference inference = Inference.builder()
.query(inferenceRequest.query())
.user(user)
.whenMade(inferenceRequest.whenCreated())
// .response(responseData)
.response(inferenceRequest.query())
.build();
final int saveInferenceId = inferenceRepository.save(inference).getId();
return InferenceResponse.builder()
.id(saveInferenceId)
.response(inferenceRequest.query()).build();//Will replace it with response once it is ready 
}

public void deleteInference(int id,Principal principal) throws AuthException{

final User user = Utils.getConnectedUser(principal);
final Inference inference = inferenceRepository.findById(id).orElseThrow();
//We didn't totally remove it from db as we might need it for training but it is no more related to the user
Utils.unrelateToUser(user, inference);
inferenceRepository.save(inference);
System.out.println(true);

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
