package com.graduationproject.project.authentication;


import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor 
public class AuthenticationRequest {
    private String username;
    private String password;    
}