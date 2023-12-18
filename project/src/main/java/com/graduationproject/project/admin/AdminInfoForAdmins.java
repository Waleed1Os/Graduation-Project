package com.graduationproject.project.admin;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminInfoForAdmins {
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private List<BanDTO> bans;
}
