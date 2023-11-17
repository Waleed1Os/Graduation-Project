package com.graduationproject.project.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graduationproject.project.project.ProjectTDO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
private final AdminService adminService;
    @GetMapping("projects/false")
    public ResponseEntity<ProjectTDO> getFalseResponses(){
        return ResponseEntity.ok(adminService.getReportedResponses());
    }

}
