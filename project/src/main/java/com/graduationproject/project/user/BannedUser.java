package com.graduationproject.project.user;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class BannedUser {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer Id;    
@ManyToOne(targetEntity=User.class,fetch = FetchType.LAZY)
private User admin;
@ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
private User user;
private Date whenBanned;
private String reason;    
}
