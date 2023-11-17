package com.graduationproject.project.authentication;

import java.io.IOException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.project.Checker;
import com.graduationproject.project.configuration.JwtService;
import com.graduationproject.project.token.Token;
import com.graduationproject.project.token.TokenRepository;
import com.graduationproject.project.user.Role;
import com.graduationproject.project.user.User;
import com.graduationproject.project.user.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
private final UserRepository userRepository;
private final TokenRepository tokenRepository;
private final JwtService jwtService;
private final PasswordEncoder passwordEncoder;
private final AuthenticationManager authenticationManager;


public AuthenticationResponse register(RegisterRequest request) {
final String email= request.getEmail();
if(!Checker.cheeckEmail(email)){
    throw new InputMismatchException("Email is not correct"); 
}
final User user = User
.builder()
.whenCreated(new Date())
.email(email)
.firstName(request.getFirstName())
.lastName(request.getLastName())
.password(passwordEncoder.encode(request.getPassword()))
.role(Role.CLIENT)
.username(request.getUsername()).build();
final User savedUser=userRepository.save(user);
final String accessToken=jwtService.generateToken(user);
final String refreshToken=jwtService.generateRefreshToken(user);
saveUserToken(accessToken, savedUser);
return AuthenticationResponse
.builder()
.accessToken(accessToken)
.refreshToken(refreshToken).build();
}
public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
final String username=authenticationRequest.getUsername();    
authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,authenticationRequest.getPassword()));
final User user = userRepository.findByUsername(username).orElseThrow();
final String accessToken=jwtService.generateToken(user);
final String refreshToken=jwtService.generateRefreshToken(user);
revokeAllUserTokens(user);
saveUserToken(accessToken, user);
return AuthenticationResponse
.builder()
.accessToken(accessToken)
.refreshToken(refreshToken)
.build();
}
 private void revokeAllUserTokens(User user) {
    List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
private void saveUserToken(String jwt,User user){
    final Token token= Token
    .builder()
    .user(user)
    .token(jwt)
    .build();
    tokenRepository.save(token);
}
public void refreshToken(HttpServletRequest request,
  HttpServletResponse response
  ) throws IOException {
final String header=request.getHeader(HttpHeaders.AUTHORIZATION);
if(header==null){
  return;
}
final String refreshToken=header.substring(7);
final String username=jwtService.extractUsername(refreshToken);
if(username!=null){
final User user= userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
if(jwtService.isTokenValid(user, username)){
final String accessToken= jwtService.generateToken(user);
revokeAllUserTokens(user);
saveUserToken(accessToken, user);
final AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);  
}

}


}

}
