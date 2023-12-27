package com.graduationproject.project;

import java.security.Principal;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.graduationproject.Ownable;
import com.graduationproject.project.user.User;

import jakarta.security.auth.message.AuthException;
import lombok.experimental.UtilityClass;

@UtilityClass
/**
 * A class used to reduce the amount of code used to do some tasks
 */
public class Utils {

public static User getConnectedUser(Principal connedctedUser){
return (User)((UsernamePasswordAuthenticationToken)connedctedUser).getPrincipal();
}

public static boolean isTheOwner(User user,Ownable iten){
   return iten.getUser().equals(user); 
}

/**
 * makes the item unrelated to the user by making the user null so we avoid removing the item from DB
 * @throws AuthException if the user is not the owner of the item
 */
public static void unrelateToUser(User user,Ownable item) throws AuthException{
    System.out.println(user.toString());
    System.out.println(item.getUser());
    if(!isTheOwner(user, item)){
        throw new AuthException("Unaothorized");
    }
    item.setUser(null);
}

}

