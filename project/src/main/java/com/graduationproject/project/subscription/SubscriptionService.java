package com.graduationproject.project.subscription;

import static com.graduationproject.project.subscription.SubscriptionType.*;


import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class SubscriptionService {
// private final SubscriptionRepository subscriptionRepository;   
public Map<SubscriptionType,Double> getSubscriptionPlans(){
    return Map.of(MONTHLY,MONTHLY.getPrice(),SEMIANNUAL,SEMIANNUAL.getPrice(),ANNUALLY,ANNUALLY.getPrice());
}
}
