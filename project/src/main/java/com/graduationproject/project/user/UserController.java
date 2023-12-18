package com.graduationproject.project.user;




import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("user/management")
@RequiredArgsConstructor
public class UserController {
private final UserService userService;
    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String username){
        return ResponseEntity.ok(userService.getUser(username));
    }
    @PatchMapping("change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request,Principal connectedUser){
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("modify-account")
    public ResponseEntity<?> modifyAccount(@RequestBody ModifyAccountRequest request,Principal connectedUser){
        userService.ModifyAccount(request, connectedUser);
        return ResponseEntity.ok().build();
    }

}