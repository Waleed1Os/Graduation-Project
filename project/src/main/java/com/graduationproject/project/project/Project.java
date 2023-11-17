package com.graduationproject.project.project;

import java.util.Date;

import com.graduationproject.project.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder.Default;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "projects")
public class Project {
    
    @Id
    private Integer id;
    @ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
    private User user;
    private String query;
    private String response;
    private Date whenMade;
    @Default
    private boolean correct=true;
}
