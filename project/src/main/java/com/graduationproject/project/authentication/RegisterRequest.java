package com.graduationproject.project.authentication;





public record RegisterRequest(String firstName,
 String lastName,    
 String username,
 String email,
 String password,
 boolean tfaEnabled) {

}
