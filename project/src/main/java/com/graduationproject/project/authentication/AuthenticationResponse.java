package com.graduationproject.project.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class AuthenticationResponse {
private final String accessToken;
private final String refreshToken;
}
