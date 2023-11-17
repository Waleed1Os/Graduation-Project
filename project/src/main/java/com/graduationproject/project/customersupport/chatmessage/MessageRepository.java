package com.graduationproject.project.customersupport.chatmessage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Integer>{
    
}
