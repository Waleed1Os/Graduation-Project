package com.graduationproject.project.inference;

import java.util.Date;
import java.util.List;

import com.graduationproject.project.user.User;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
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
import lombok.Builder.Default;

@Getter
@Setter
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Table(name = "inferences")
public class Inference {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
    private User user;
    private String query;
    private String response;
    private Date whenMade;
    @Default
    private boolean correct=true;
    @ElementCollection(fetch = FetchType.LAZY,targetClass = String.class)
    private List<String> incorrectWords;
}
