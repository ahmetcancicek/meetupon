package com.meetupon.auth.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class LoginRequest {

    @NotBlank(message = "common.client.requiredField")
    private String username;

    @NotEmpty(message = "common.client.requiredField")
    private String password;
}
