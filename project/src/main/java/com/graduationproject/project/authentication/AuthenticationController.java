package com.graduationproject.project.authentication;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {
private final AuthenticationService authenticationService;    

@PostMapping("/login")
@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
    return ResponseEntity.ok(authenticationService.authenticate(request));
} 
@PostMapping("/signup")
public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
    return ResponseEntity.ok(authenticationService.register(request));
}
@PostMapping("/refresh-token")
public void refreshToken(HttpServletRequest request,
      HttpServletResponse response) throws IOException{
    authenticationService.refreshToken(request,response);
}

}
