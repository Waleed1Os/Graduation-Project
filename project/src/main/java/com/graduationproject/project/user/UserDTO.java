package com.graduationproject.project.user;

import java.util.Date;
import java.util.List;

import com.graduationproject.project.inference.InferenceDTO;



public record UserDTO(String firstName,String lastName,
 String username,
 String email,
 List<InferenceDTO> inferences, 
 Date whenCreated) {
}
