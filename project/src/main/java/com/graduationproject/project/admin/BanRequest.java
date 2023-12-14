package com.graduationproject.project.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BanRequest {
private final String adminUsername;
private final String clientUsername;
private final String reason;     
}
