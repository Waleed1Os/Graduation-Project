package com.graduationproject.project.authentication;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterRequest {
private final String firstName;
private final String lastName;    
private final String username;
private final String email;
private final String password;
}
