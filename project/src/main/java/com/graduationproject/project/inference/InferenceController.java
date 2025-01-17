package com.graduationproject.project.inference;

import java.security.Principal;
import java.util.List;
import java.util.Set;

// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// import com.graduationproject.project.Checkers;

import jakarta.security.auth.message.AuthException;
// import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("inference")
@RequiredArgsConstructor
public class InferenceController {
    private final InferenceService inferenceService;
    @GetMapping("/get/all")
    public ResponseEntity<List<InferenceDTO>> getInferences(
        // @RequestParam
        //  int page,
         Principal coneedctedUser){
        return ResponseEntity.ok(inferenceService.getInferences(coneedctedUser));
            // PageRequest.of(page, 10,Sort.by("id")),coneedctedUser));
    }
    
    @DeleteMapping("remove/{id}")
    public void deleteInference(@PathVariable int id,Principal connectedUser) throws AuthException{
        // return ResponseEntity.ok(inferenceService.deleteInference(id, connectedUser));
        inferenceService.deleteInference(id, connectedUser);
    }
    
    @PostMapping("Report/{id}")
    public ResponseEntity<?> reportFalseResponse(@PathVariable int id,@RequestBody Set<String> incorrectWords,Principal principal) throws AuthException{
        inferenceService.reportFalseResponse(id, incorrectWords, principal);
        return ResponseEntity.ok().build();
    }
    
@PostMapping(value ="infere",produces = "application/json; charset=UTF-8")
public ResponseEntity<InferenceResponse> infereAI(
    
    // @Pattern(regexp = Checkers.INFERENCE_TO_AI_REGEX) 
    @RequestBody InferenceRequest inferenceRequest,Principal connedctedUser) {    
return new ResponseEntity<InferenceResponse>(inferenceService.infereAI(inferenceRequest,connedctedUser),HttpStatusCode.valueOf(200));

} 

}
