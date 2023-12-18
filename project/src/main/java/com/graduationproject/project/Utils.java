package com.graduationproject.project;

import java.security.Principal;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.graduationproject.project.user.User;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {

public static User getConnectedUser(Principal connedctedUser){
return (User)((UsernamePasswordAuthenticationToken)connedctedUser).getPrincipal();
}
    

}
