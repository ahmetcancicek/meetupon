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
public class UpdateUserRequest {
    @Email
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    private String password;
    private String firstName;
    private String lastName;
    private RoleName roleName;
    private Boolean enabled;
    private Boolean registerAsAdmin;
}
