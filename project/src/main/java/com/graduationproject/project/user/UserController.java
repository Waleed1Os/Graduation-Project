package com.graduationproject.project.user;




import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserTDO> getUser(@PathVariable int id){
        return ResponseEntity.ok(userService.getUser(id));
    }

}