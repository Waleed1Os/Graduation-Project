package com.graduationproject.project.admin;

import java.util.List;


public record AdminInfoForAdmins(String userName,
     String email,
     String firstName,
     String lastName,
     List<BanDTO> bans) {
     
}
