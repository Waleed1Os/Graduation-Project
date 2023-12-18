package com.graduationproject.project.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ModifyAccountRequest {
private String firstName,lastName;    
}
