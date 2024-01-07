package com.graduationproject.project.authentication;

import lombok.Builder;

@Builder
public record VerificationRequest(String principal,String code) {}
