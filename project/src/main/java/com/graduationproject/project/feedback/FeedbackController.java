package com.graduationproject.project.feedback;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("feedback")
@RequiredArgsConstructor
public class FeedbackController {
private final FeedbackService feedbackService;

@GetMapping("get/all")
public ResponseEntity<List<FeedbackDTO>> getFeedbacks(Principal connectedUser) {
return ResponseEntity.ok(feedbackService.getFeedbacks(connectedUser));    
}


@PostMapping("send")
public int sendFeedback(@RequestBody FeedbackDRO feedback, Principal connectedUser) {
return feedbackService.sendFeedback(feedback, connectedUser);
}

@DeleteMapping("remove/{id}")
public void deleteFeedback(@PathVariable int id, Principal connectedUser) {
this.feedbackService.deleteFeedback(id, connectedUser);
}

}
