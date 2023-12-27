package com.graduationproject.project.authentication;

import lombok.Builder;


@Builder
public record AuthenticationResponse( String accessToken,
 String refreshToken) {

}
