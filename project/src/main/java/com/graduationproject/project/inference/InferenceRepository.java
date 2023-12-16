package com.graduationproject.project.inference;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InferenceRepository extends JpaRepository<Inference,Integer>{
public Page<Inference> findByCorrect(boolean correct,Pageable pageable); 
@Query("SELECT new com.graduationproject.project.inference.InferenceDTO(i.id, i.query, i.response, i.whenMade, i.correct) FROM Inference i WHERE i.correct = :correct")
public List<InferenceDTO> findByCorrectDTO(@Param("correct") boolean correct,Pageable pageable);


}
