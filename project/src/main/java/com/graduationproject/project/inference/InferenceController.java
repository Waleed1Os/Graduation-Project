package com.graduationproject.project.inference;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduationproject.project.Checkers;

import jakarta.security.auth.message.AuthException;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("inference")
@RequiredArgsConstructor
public class InferenceController {
private final InferenceService inferenceService;
@GetMapping("/get/all")
public ResponseEntity<List<InferenceDTO>> getInferences(@RequestParam int page,Principal coneedctedUser){
    return ResponseEntity.ok(inferenceService.getInferences(PageRequest.of(page, 10,Sort.by("id")),coneedctedUser));
}

@DeleteMapping("remove/{id}")
public ResponseEntity<?> deleteInference(@PathVariable int id,Principal connectedUser) throws AuthException{
    inferenceService.deleteInference(id, connectedUser);
    return ResponseEntity.ok().build();
}

@PostMapping("Report/{id}")
public ResponseEntity<?> reportFalseResponse(@PathVariable int id,@RequestBody List<String> incorrectWords,Principal principal) throws AuthException{
inferenceService.reportFalseResponse(id, incorrectWords, principal);
return ResponseEntity.ok().build();
}

@PostMapping("infere")
public void infereAI(@Pattern(regexp = Checkers.INFERENCE_TO_AI_REGEX) @RequestBody String query,Principal connedctedUser){    
inferenceService.infereAI(query,connedctedUser);

} 

}
