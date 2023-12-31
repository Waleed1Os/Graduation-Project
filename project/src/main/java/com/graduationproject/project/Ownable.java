package com.graduationproject.project;

import com.graduationproject.project.user.User;

/**
 *An interface that facilitates testing whether or not the user is the owner of some data
 *this can be used in Utility classes
 *@see Utils 
 */
public interface Ownable{
public User getUser();
public void setUser(User user);   
}
