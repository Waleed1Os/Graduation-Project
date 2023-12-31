package com.graduationproject.project.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;

@Builder
@JsonInclude(content = Include.NON_EMPTY)
public record AuthenticationResponse(
    @JsonProperty("access-token") String accessToken,
    @JsonProperty("refresh-token")    String refreshToken,
    boolean tfaEnabled,
    String secretImageURi
    ) {}
