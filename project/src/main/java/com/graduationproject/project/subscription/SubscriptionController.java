package com.graduationproject.project.subscription;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("subscription")
@RequiredArgsConstructor
public class SubscriptionController {
private final SubscriptionService subscriptionService;
@GetMapping("plans")
public ResponseEntity<Map<SubscriptionType,Double>> getSubscriptionPlans(){
return ResponseEntity.ok(subscriptionService.getSubscriptionPlans());
}    
@PostMapping("subscribe")
public ResponseEntity<SubscriptionDTO> subscribe(@RequestBody String type,Principal connectedUser){
    System.out.println(type);
return ResponseEntity.ok(subscriptionService.subscribe(type, connectedUser));    
}

@GetMapping("get/all")
public ResponseEntity<List<SubscriptionDTO>> getSubscriptions(Principal connectedUser) {
    
return ResponseEntity.ok(subscriptionService.getSubscriptions(connectedUser));

}

}
