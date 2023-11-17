package com.graduationproject.project.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class RegisterRequest {
private final String firstName;
private final String lastName;    
private final String username;
private final String email;
private final String password;
}
