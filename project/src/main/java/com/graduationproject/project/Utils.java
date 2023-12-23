package com.graduationproject.project;

import java.security.Principal;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.graduationproject.project.user.User;

import lombok.experimental.UtilityClass;

@UtilityClass
/**
 * A class used to reduce the amount of code used to do some tasks
 */
public class Utils {

public static User getConnectedUser(Principal connedctedUser){
return (User)((UsernamePasswordAuthenticationToken)connedctedUser).getPrincipal();
}
    

}
