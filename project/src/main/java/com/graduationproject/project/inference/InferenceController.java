package com.graduationproject.project.inference;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("inference")
@RequiredArgsConstructor
public class InferenceController {
private final InferenceService inferenceService;
@GetMapping("/get/all")
public ResponseEntity<List<Inference>> getProjects(Principal coneedctedUser){
    return ResponseEntity.ok(inferenceService.getInferences(coneedctedUser));
}

@DeleteMapping("remove/{id}")
public ResponseEntity<?> deleteInference(@PathVariable int id,Principal connectedUser) throws AuthException{
    inferenceService.deleteInference(id, connectedUser);
    return ResponseEntity.ok().build();
}
}
