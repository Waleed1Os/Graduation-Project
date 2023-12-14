package com.graduationproject.project.user;

import java.util.Date;
import java.util.List;

import com.graduationproject.project.inference.InferenceDTO;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {
private Integer id;    
private String firstName,lastName;
private String username;
private String email;
private List<InferenceDTO> projects; 
private Date whenCreated;
}
