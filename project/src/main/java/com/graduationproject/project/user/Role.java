package com.graduationproject.project.user;

import static com.graduationproject.project.user.Permission.*;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;

public enum Role {
ADMIN(Set.of(ADMIN_READ,ADMIN_CREATE,ADMIN_DELETE,ADMIN_WRITE,ADMIN_UPDATE)),
CLIENT(Set.of(USER_CREATE,USER_READ,USER_UPDATE,USER_WRITE,USER_DELETE));

@Getter
private final Set<Permission> permissions;
private Role(Set<Permission> premissions){
this.permissions=premissions;
}

public Set<SimpleGrantedAuthority> getAuthorities(){
    Set<SimpleGrantedAuthority> authorities= getPermissions().stream().map(premission -> new SimpleGrantedAuthority(premission.getPermission())).collect(Collectors.toSet());
    authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
    return authorities;
}
}
