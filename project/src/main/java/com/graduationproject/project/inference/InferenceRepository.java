package com.graduationproject.project.inference;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.graduationproject.project.user.User;

@Repository
public interface InferenceRepository extends JpaRepository<Inference,Integer>{
public Page<Inference> findByCorrect(boolean correct,Pageable pageable); 
@Query("SELECT new com.graduationproject.project.inference.InferenceDTO(i.id, i.query, i.response, i.whenMade, i.correct, i.incorrectWords) FROM Inference i WHERE i.correct = :correct")
public Page<InferenceDTO> findByCorrectDTO(@Param("correct") boolean correct,Pageable pageable);
@Query("SELECT new com.graduationproject.project.inference.InferenceDTO(i.id, i.query, i.response, i.whenMade, i.correct, i.incorrectWords) FROM Inference i WHERE i.user = :user")
public List<InferenceDTO> findByUsertDTO(@Param("user") User user
// ,Pageable pageable
);

}
