package com.graduationproject.project.user;


import lombok.Getter;

public enum Permission {

ADMIN_READ("admin:read"),
ADMIN_WRITE("admin:write"),
ADMIN_DELETE("admin:delete"),
ADMIN_UPDATE("admin:update"),
ADMIN_CREATE("admin:create"),
USER_READ("user:read"),
USER_WRITE("user:write"),
USER_DELETE("user:delete"),
USER_UPDATE("user:update"),
USER_CREATE("user:create");
@Getter
private final String permission;
private Permission(String premission){
this.permission=premission;
    }

}
