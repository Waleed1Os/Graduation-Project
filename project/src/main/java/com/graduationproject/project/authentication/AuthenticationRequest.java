package com.graduationproject.project.authentication;

import jakarta.validation.constraints.NotNull;

public record AuthenticationRequest(
   @NotNull String principle,
   @NotNull String password) {}