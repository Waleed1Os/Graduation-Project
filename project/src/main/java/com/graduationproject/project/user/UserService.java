package com.graduationproject.project.user;







import java.security.Principal;
import java.util.Date;
// import java.util.Date;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.graduationproject.project.Utils;
import com.graduationproject.project.mail.EMailSender;

import jakarta.mail.MessagingException;
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

public void changePassword(ChangePasswordRequest request,Principal principal) throws MessagingException{
final User loggedInUser = Utils.getConnectedUser(principal);
if(!passwordEncoder.matches(request.currentPassword(),loggedInUser.getPassword())){
    throw new IllegalStateException("Wrong password");
}
if(!request.newPassword().equals(request.newPasswordConfirmation())){
    throw new IllegalStateException("Passwords don't match");
}
loggedInUser.setPassword(passwordEncoder.encode(request.newPassword()));
userRepository.save(loggedInUser);
eMailSender.sendEmail(loggedInUser.getEmail(),"Your Password has been Changed! ","changepassword",Map.of("timestamp", new Date()));
}
public void ModifyAccount(ModifyAccountRequest request,Principal connectedUser){
final User user = Utils.getConnectedUser(connectedUser);
user.setFirstName(request.firstName());
user.setLastName(request.lastName());
userRepository.save(user);
}

}

