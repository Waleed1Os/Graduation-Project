package com.graduationproject.project.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Integer>{
public Page<Feedback> findByType(Pageable pageable,FeedbackType type);    
}
