package com.graduationproject.project.project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer>{
    public List<Project> findByCorrect(boolean correct); 
}
