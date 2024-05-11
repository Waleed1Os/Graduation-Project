package com.graduationproject.project.subscription;

import static com.graduationproject.project.subscription.SubscriptionType.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.graduationproject.project.Utils;
import com.graduationproject.project.user.User;
import com.graduationproject.project.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
private final SubscriptionRepository subscriptionRepository;   
private final UserRepository userRepository;
private final ModelMapper modelMapper;
public Map<SubscriptionType,Double> getSubscriptionPlans(){
    return Map.of(MONTHLY,MONTHLY.getPrice(),SEMIANNUAL,SEMIANNUAL.getPrice(),ANNUALLY,ANNUALLY.getPrice());
}

public SubscriptionDTO subscribe(String type,Principal connectedUser){
final User user = Utils.getConnectedUser(connectedUser);
final Subscription subscription = Subscription.builder()
.whenMade(new Date())
.user(user)
// .price(type.getPrice())
.type(SubscriptionType.valueOf(type))
.build();
subscription.setPrice(subscription.getType().getPrice());
if(user.getPremium()==null||user.getPremium().before(new Date())){
user.setPremium(new Date(System.currentTimeMillis()+subscription.getType().getPeriod()));
userRepository.save(user);
final Subscription savedSubscription = subscriptionRepository.save(subscription);
return modelMapper.map(savedSubscription,SubscriptionDTO.class);
}
return null;
}

public List<SubscriptionDTO> getSubscriptions(Principal connectedUser) {
    final User user = Utils.getConnectedUser(connectedUser);
    final List<SubscriptionDTO> subscriptions = subscriptionRepository.findByUser(user).stream().map(subscription -> modelMapper.map(subscription, SubscriptionDTO.class)).collect(Collectors.toList());
return subscriptions;
}

}
