package com.meetupon.auth.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class RegistrationRequest {
    @Email
    @NotNull
    @NotBlank
    private String email;
    @NotBlank
    private String username;
    @NotNull
    private String password;
    private String firstName;
    private String lastName;
    private Boolean registerAsAdmin;
}
