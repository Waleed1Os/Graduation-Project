package com.graduationproject.project.admin;


import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graduationproject.project.feedback.FeedbackDTO;
import com.graduationproject.project.feedback.FeedbackType;
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
    @GetMapping("feedbacks/{type}/{page}")
    public ResponseEntity<List<FeedbackDTO>> getFeedbacksByType(@PathVariable FeedbackType type,@PathVariable int page){
        return ResponseEntity.ok(adminService.getFeedbacksByType(PageRequest.of(page,10,Sort.Direction.ASC,"whenMade"),type));
    }

}
