package com.graduationproject.project;

import java.security.Principal;

public interface CRUD<T,R,V> {

public int create(R request,Principal connectedUser);    
public void delete(int id,Principal connectedUser);
public void update(Object request,Principal connectedUser);
public V read(int id,Principal connectedUser);

}
