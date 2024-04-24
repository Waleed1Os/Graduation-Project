package com.graduationproject.project.authentication;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.samstevens.totp.exceptions.CodeGenerationException;
import jakarta.mail.MessagingException;
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
public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    final AuthenticationResponse response = authenticationService.register(request);
    if (request.tfaEnabled()) {
        return ResponseEntity.ok(response);
    }
    return ResponseEntity.accepted().body(response);
}
@PostMapping("/refresh-token")
public void refreshToken(HttpServletRequest request,
      HttpServletResponse response) throws IOException{
    authenticationService.refreshToken(request,response);
}

@PostMapping("/verify")
public ResponseEntity<?> verifyCode(@RequestBody VerificationRequest verificationRequest){
return ResponseEntity.ok(authenticationService.verifyCode(verificationRequest));
}

@PostMapping("reset-password")
public void resetPassword(@RequestBody String email) throws MessagingException, CodeGenerationException{
this.authenticationService.resetPassword(email);
}

@GetMapping("email/{email}")
public ResponseEntity<Boolean> isEmailReserved(@PathVariable String email){
    return ResponseEntity.ok(this.authenticationService.isEmailReserved(email));
}

@GetMapping("username/{username}")
public ResponseEntity<Boolean> isUsernameReserved(@PathVariable String username){
    return ResponseEntity.ok(this.authenticationService.isUsernameReserved(username));
}

}
