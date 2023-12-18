package com.graduationproject.project.user;







import java.security.Principal;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.graduationproject.project.Utils;
import com.graduationproject.project.mail.EMailSender;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class UserService {
private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;
private final EMailSender eMailSender;

public UserDTO getUser(String usernmae){
    User user=userRepository.findByUsername(usernmae).orElseThrow(()->new UsernameNotFoundException("User not found"));
    ModelMapper mapper=new ModelMapper();
    return mapper.map(user, UserDTO.class);
}

public void changePassword(ChangePasswordRequest request,Principal principal){
final User loggedInUser = Utils.getConnectedUser(principal);
if(!passwordEncoder.matches(request.getCurrentPassword(),loggedInUser.getPassword())){
    throw new IllegalStateException("Wrong password");
}
if(!request.getNewPassword().equals(request.getNewPasswordConfirmation())){
    throw new IllegalStateException("Passwords don't match");
}
loggedInUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
userRepository.save(loggedInUser);
eMailSender.sendEmail(loggedInUser.getEmail(), "Password has been changed!","Your password has been changed at "+new Date(), false);
}
public void ModifyAccount(ModifyAccountRequest request,Principal connectedUser){
final User user = Utils.getConnectedUser(connectedUser);
user.setFirstName(request.getFirstName());
user.setLastName(request.getLastName());
userRepository.save(user);
}

}

