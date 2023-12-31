package com.graduationproject.project.user;

import lombok.Builder;


@Builder
public record ChangePasswordRequest(String currentPassword,
 String newPassword,
 String newPasswordConfirmation) {
}
