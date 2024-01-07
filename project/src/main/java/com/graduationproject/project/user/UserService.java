package com.graduationproject.project.user;







import java.io.IOException;
import java.security.Principal;
// import java.util.Date;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.project.Utils;
import com.graduationproject.project.mail.EMailSender;
import com.graduationproject.project.tfa.TfaService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class UserService {
private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;
private final EMailSender eMailSender;
private final TfaService tfaService;
public UserDTO getUser(String usernmae){
    User user=userRepository.findByUsername(usernmae).orElseThrow(()->new UsernameNotFoundException("User not found"));
    ModelMapper mapper=new ModelMapper();
    return mapper.map(user, UserDTO.class);
}

public void changePassword(ChangePasswordRequest request,Principal connectedUser) throws MessagingException{
final User user = Utils.getConnectedUser(connectedUser);
if(!passwordEncoder.matches(request.currentPassword(),user.getPassword())){
    throw new IllegalStateException("Wrong password");
}
if(!request.newPassword().equals(request.newPasswordConfirmation())){
    throw new IllegalStateException("Passwords don't match");
}
user.setPassword(passwordEncoder.encode(request.newPassword()));
userRepository.save(user);
eMailSender.sendEmail(user.getEmail(),"Your Password has been Changed! ","changepassword",Map.of("name",user.getFirstName()));
}
public void ModifyAccount(ModifyAccountRequest request,Principal connectedUser){
final User user = Utils.getConnectedUser(connectedUser);
user.setFirstName(request.firstName());
user.setLastName(request.lastName());
userRepository.save(user);
}

public void disableOrEnableTfa(HttpServletRequest request,HttpServletResponse response,String code,Principal connectedUser) throws StreamWriteException, DatabindException, IOException{

final User user = Utils.getConnectedUser(connectedUser);
if (user.isTfaEnabled()) {
if(tfaService.isOtpNotValid(user.getSecret(),code)){
    throw new BadCredentialsException("Code is incorrect");
}    
user.setSecret(null);
user.setTfaEnabled(false);
userRepository.save(user);
}
final String secret = tfaService.generateNewSecret();
user.setSecret(secret);
userRepository.save(user);
new ObjectMapper().writeValue(response.getOutputStream(), tfaService.generateQrCodeImageUri(secret));

}
public String getQRImageUri(Principal principal){
final User user = Utils.getConnectedUser(principal);
return tfaService.generateQrCodeImageUri(user.getSecret());
}

public void verifyCode(String code,Principal connectedUser){
final User user = Utils.getConnectedUser(connectedUser);
if(tfaService.isOtpNotValid(user.getSecret(),code)){
    throw new BadCredentialsException("Code is incorrect");
}    
user.setTfaEnabled(true);
userRepository.save(user);
}

}

