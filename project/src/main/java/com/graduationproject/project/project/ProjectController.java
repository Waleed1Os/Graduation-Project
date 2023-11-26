package com.graduationproject.project.project;

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
public class ProjectController {
private final ProjectService projectService;
@GetMapping("/get/all")
public ResponseEntity<List<Project>> getProjects(@RequestBody int id){
    return ResponseEntity.ok(projectService.getProjects(id));
}

}
