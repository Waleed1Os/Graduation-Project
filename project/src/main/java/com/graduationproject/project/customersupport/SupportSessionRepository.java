package com.graduationproject.project.customersupport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportSessionRepository extends JpaRepository<SupportSession,Integer>{
    
}
