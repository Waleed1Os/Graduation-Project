package com.graduationproject.project.feedback;

import java.security.Principal;
import java.util.Date;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.graduationproject.project.Utils;
import com.graduationproject.project.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackService {
private final FeedbackRepository feedbackRepository;
 

public int sendFeedback(FeedbackDRO feedbackRequest,Principal connectedUser){
final User user = Utils.getConnectedUser(connectedUser);
final Feedback feedback = Feedback.builder()
.complaint(feedbackRequest.getComplaint())
.type(feedbackRequest.getType())
.user(user)
.whenMade(new Date())
.build();
final int id = feedbackRepository.save(feedback).getId();
return id;
}

public void deleteFeedback(int id,Principal connectedUser){
final User user = Utils.getConnectedUser(connectedUser);
final Feedback feedback = feedbackRepository.findById(id).orElseThrow();
if (!feedback.getUser().equals(user)) {
 throw new AccessDeniedException("Feedback is not related to this user");   
}
feedbackRepository.delete(feedback);
}

}
