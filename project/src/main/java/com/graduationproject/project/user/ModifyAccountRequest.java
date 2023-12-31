package com.graduationproject.project.user;

import lombok.Builder;


@Builder
public record ModifyAccountRequest(String firstName,String lastName) {
}
