package com.graduationproject.project.user;




import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("user/management")
@RequiredArgsConstructor
public class UserController {
private final UserService userService;
    @PatchMapping("change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request,Principal connectedUser) throws MessagingException{
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("modify-account")
    public ResponseEntity<?> modifyAccount(@RequestBody ModifyAccountRequest request,Principal connectedUser){
        userService.ModifyAccount(request, connectedUser);
        return ResponseEntity.ok().build();
    }

}