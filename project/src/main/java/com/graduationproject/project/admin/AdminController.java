package com.graduationproject.project.admin;


import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graduationproject.project.inference.InferenceDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
private final AdminService adminService;
    @GetMapping("projects/incorrect/{page}")
    public ResponseEntity<List<InferenceDTO>> getFalseResponses(@PathVariable int page){
        return ResponseEntity.ok(adminService.getReportedResponses(PageRequest.of(page,10)));
    }

}
