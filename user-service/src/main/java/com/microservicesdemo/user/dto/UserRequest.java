package com.microservicesdemo.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class UserRequest {
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
    private RoleName roleName;
    private Boolean enabled;
    private Boolean registerAsAdmin;
}
