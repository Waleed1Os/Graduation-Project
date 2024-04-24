package com.graduationproject.project.user;

import java.util.Date;



public record UserDTO(
String firstName,
String lastName,
String username,
String email,
Date premium,
Date whenCreated,
 byte[] pfp
 ) {}
