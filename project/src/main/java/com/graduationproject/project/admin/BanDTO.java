package com.graduationproject.project.admin;

import java.util.Date;


public record BanDTO( int id,    
String clientUsername,
 String reason,
Date whenBanned) {
    
}
