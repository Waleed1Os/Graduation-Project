package com.graduationproject.project.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageRequest {
private final String adminUsername;
private final int sessionId;
private final String message;
}
