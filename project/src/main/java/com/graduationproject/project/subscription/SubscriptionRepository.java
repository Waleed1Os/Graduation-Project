package com.graduationproject.project.subscription;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.graduationproject.project.user.User;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Integer>{
public List<Subscription> findByUser(User user);    
}
