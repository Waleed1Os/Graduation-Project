package com.graduationproject.project.subscription;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

}
