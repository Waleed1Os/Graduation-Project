package com.graduationproject.project.feedback;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.graduationproject.project.user.User;


@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Integer>{
public Page<Feedback> findByType(Pageable pageable,FeedbackType type);

public List<Feedback> findByUser(User user);    
}
