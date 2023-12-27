package com.graduationproject.project.authentication;

import java.io.IOException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.AlreadyBuiltException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.project.Checker;
import com.graduationproject.project.Utils;
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
final String email= request.email();
if(!Checker.checkEmail(email)){
    throw new InputMismatchException("Email is not correct"); 
}

if(userRepository.existsByUsername(request.username())){
  throw new AlreadyBuiltException("UserName has been already used");
}
final User user = User
.builder()
.whenCreated(new Date())
.email(email)
.firstName(request.firstName())
.lastName(request.lastName())
.password(passwordEncoder.encode(request.password()))
.role(Role.CLIENT)
.username(request.username()).build();
final User savedUser = userRepository.save(user);
final String accessToken=jwtService.generateToken(user);
final String refreshToken=jwtService.generateRefreshToken(user);
saveUserToken(accessToken, savedUser);
return AuthenticationResponse
.builder()
.accessToken(accessToken)
.refreshToken(refreshToken).build();
}
public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
final String usernameOrEmail=authenticationRequest.principle();    
//I made this code to reduce queries to DB so instead of loading the user again from DB i get the authenticated user as soon as it is confirmed to be authenticated
final Authentication principal = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernameOrEmail,authenticationRequest.password()));
final User user = Utils.getConnectedUser(principal);
// Checker.checkEmail(usernameOrEmail)?userRepository.findByEmail(usernameOrEmail).orElseThrow():userRepository.findByUsername(usernameOrEmail).orElseThrow();
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
    List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getUsername());
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
public void refreshToken(
  HttpServletRequest request,
  HttpServletResponse response
) throws IOException {
final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
  return;
}

final String refreshToken = authHeader.substring(7);
final String username = jwtService.extractUsername(refreshToken);
if (username != null) {
final User user = this.userRepository.findByUsername(username)
      .orElseThrow(()-> new UsernameNotFoundException("User not found"));
if (jwtService.isTokenValid(user,refreshToken)) {
final String accessToken = jwtService.generateToken(user);
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
