package com.graduationproject.project.feedback;


import java.util.Date;

import com.graduationproject.Ownable;
import com.graduationproject.project.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "feedbacks")
public class Feedback implements Ownable{
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;
private String complaint;
private Date whenMade;
@Enumerated(EnumType.STRING)
private FeedbackType type;
@ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
private User user;
}

