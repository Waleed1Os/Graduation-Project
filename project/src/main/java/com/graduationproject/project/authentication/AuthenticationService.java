package com.graduationproject.project.authentication;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.AlreadyBuiltException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.project.Checkers;
import com.graduationproject.project.Utils;
import com.graduationproject.project.configuration.JwtService;
import com.graduationproject.project.mail.EMailSender;
import com.graduationproject.project.tfa.TfaService;
import com.graduationproject.project.token.Token;
import com.graduationproject.project.token.TokenRepository;
import com.graduationproject.project.user.Role;
import com.graduationproject.project.user.User;
import com.graduationproject.project.user.UserRepository;

import dev.samstevens.totp.exceptions.CodeGenerationException;
import jakarta.mail.MessagingException;
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
  private final TfaService tfaService;
 private final EMailSender eMailSender;


  public AuthenticationResponse register(RegisterRequest request) {
    // resolved this with @Pattern annotation
    // final String email= request.email();
    // // if(!Checkers.checkEmail(email)){
    // // throw new InputMismatchException("Email is not correct");
    // // }
    /*
     * This might need a check against race condition
     * i might need to switch to artificial key to avoid it
     */
    if (userRepository.existsByUsername(request.username())) {
      throw new AlreadyBuiltException("UserName has been already used");
    }
    final User user = User
        .builder()
        .whenCreated(new Date())
        .email(request.email())
        .firstName(request.firstName())
        .lastName(request.lastName())
        .password(passwordEncoder.encode(request.password()))
        .role(Role.CLIENT)
        .tfaEnabled(request.tfaEnabled())
        .username(request.username()).
        pfp(request.pfp()).build();
    // if (request.tfaEnabled()) {
      // user.setSecret(tfaService.generateNewSecret());
      // userRepository.save(user);
      // return AuthenticationResponse.builder()
          // .tfaEnabled(true)
          // .secretImageURi(tfaService.generateQrCodeImageUri(user.getSecret()))
          // .build();
    // }
    user.setSecret(tfaService.generateNewSecret());
    userRepository.save(user);
    final String accessToken = jwtService.generateToken(user);
    final String refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(refreshToken, user);
    return AuthenticationResponse
        .builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
    final String usernameOrEmail = authenticationRequest.principle();
    // I made this code to reduce queries to DB so instead of loading the user again
    // from DB i get the authenticated user as soon as it is confirmed to be
    // authenticated
    final Principal principal = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(usernameOrEmail, authenticationRequest.password()));
    final User user = Utils.getConnectedUser(principal);
    // if (user.isTfaEnabled()) {
    //   return AuthenticationResponse
    //   .builder()
    //   .tfaEnabled(true)
    //   .secretImageURi(tfaService.generateQrCodeImageUri(user.getSecret()))
    //   .build();
    // }
    revokeAllUserTokens(user);
    final String refreshToken = jwtService.generateRefreshToken(user);
    final String accessToken = jwtService.generateToken(user);
    // Checker.checkEmail(usernameOrEmail)?userRepository.findByEmail(usernameOrEmail).orElseThrow():userRepository.findByUsername(usernameOrEmail).orElseThrow();
    saveUserToken(refreshToken, user);
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

  private void saveUserToken(String jwt, User user) {
    final Token token = Token
        .builder()
        .user(user)
        .token(jwt)
        .build();
    tokenRepository.save(token);
  }

  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }

    final String refreshToken = authHeader.substring(7);
    final String username = jwtService.extractUsername(refreshToken);
    if (username != null) {
      final User user = this.userRepository.findByUsername(username)
          .orElseThrow(() -> new UsernameNotFoundException("User not found"));
      if (jwtService.isTokenValid(user, refreshToken)) {
        final String accessToken = jwtService.generateToken(user);
        final AuthenticationResponse authResponse = AuthenticationResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }

  public AuthenticationResponse verifyCode(VerificationRequest verificationRequest) {

    final User user = Checkers.checkEmail(verificationRequest.principal())
        ? userRepository.findByEmail(verificationRequest.principal()).orElseThrow(
            () -> new UsernameNotFoundException(String.format("User not found: %S", verificationRequest.principal())))
        : userRepository.findByUsername(verificationRequest.principal()).orElseThrow(
            () -> new UsernameNotFoundException(String.format("User not found: %S", verificationRequest.principal())));
    if (tfaService.isOtpNotValid(user.getSecret(), verificationRequest.code())) {
      throw new BadCredentialsException("Code is not correct");
    }
    final String accessToken = jwtService.generateToken(user);
    final String refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(refreshToken, user);
    return AuthenticationResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        // .tfaEnabled(true)
        .build();

  }

public void resetPassword(String email) throws MessagingException, CodeGenerationException {
   if(!userRepository.existsByEmail(email)){
    throw new UsernameNotFoundException("No account with this email was found");
   }
   final User user = userRepository.findByEmail(email).get();
   eMailSender.sendEmail(email, "Reset password", "OTP is valid for 30 seconds:\n"+tfaService.generateTextOTP(user.getSecret()));
}

public boolean isEmailReserved(String email){
return userRepository.existsByEmail(email);
}

public boolean isUsernameReserved(String username){
  return userRepository.existsByUsername(username);
  }

}
