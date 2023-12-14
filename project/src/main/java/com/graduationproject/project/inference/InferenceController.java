package com.graduationproject.project.inference;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("project")
@RequiredArgsConstructor
public class InferenceController {
private final InferenceService inferenceService;
@GetMapping("/get/all")
public ResponseEntity<List<Inference>> getProjects(@RequestBody int id){
    return ResponseEntity.ok(inferenceService.getInferences(id));
}

}
