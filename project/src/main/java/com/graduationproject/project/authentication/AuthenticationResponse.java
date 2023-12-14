package com.graduationproject.project.authentication;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;



@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class AuthenticationResponse {
private final String accessToken;
private final String refreshToken;
}
