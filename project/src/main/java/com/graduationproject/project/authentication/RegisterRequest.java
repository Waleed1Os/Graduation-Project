package com.graduationproject.project.authentication;

import com.graduationproject.project.Checkers;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Pattern.Flag;

@NotNull
public record RegisterRequest(
String firstName,
String lastName,    
String username,
@Pattern(regexp = Checkers.EMAIL_REGEX,flags = Flag.CASE_INSENSITIVE)
String email,
String password,
boolean tfaEnabled) {

}
