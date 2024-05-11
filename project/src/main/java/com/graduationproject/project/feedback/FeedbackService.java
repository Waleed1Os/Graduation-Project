package com.graduationproject.project.feedback;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.graduationproject.project.Utils;
import com.graduationproject.project.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackService {
private final FeedbackRepository feedbackRepository;
 private final ModelMapper modelMapper;

public int sendFeedback(FeedbackDRO feedbackRequest,Principal connectedUser){
final User user = Utils.getConnectedUser(connectedUser);
final Feedback feedback = Feedback.builder()
.complaint(feedbackRequest.getComplaint())
.type(feedbackRequest.getType())
.user(user)
.whenMade(feedbackRequest.getWhenMade())
.build();
final int id = feedbackRepository.save(feedback).getId();
return id;
}

public void deleteFeedback(int id,Principal connectedUser){
final User user = Utils.getConnectedUser(connectedUser);
final Feedback feedback = feedbackRepository.findById(id).orElseThrow();
if (!Utils.isTheOwner(user, feedback)) {
 throw new AccessDeniedException("Feedback is not related to this user");   
}
feedbackRepository.delete(feedback);
}

public List<FeedbackDTO> getFeedbacks(Principal connectedUser) {
final User user = Utils.getConnectedUser(connectedUser);
final List<FeedbackDTO> feedbackDTOs = feedbackRepository.findByUser(user).stream().map(feedback -> modelMapper.map(feedback,FeedbackDTO.class)).collect(Collectors.toList()); 
return feedbackDTOs;

}

}
