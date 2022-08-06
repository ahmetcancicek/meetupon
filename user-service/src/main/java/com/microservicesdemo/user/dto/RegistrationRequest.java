package com.microservicesdemo.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Setter
@Getter
@ToString
public class RegistrationRequest {
    @Email
    @NotNull
    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotNull
    private String password;

    @NotNull
    private Boolean registerAsAdmin;
}
