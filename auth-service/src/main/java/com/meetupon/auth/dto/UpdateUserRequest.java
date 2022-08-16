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

    @Email(message = "common.client.notValidEmail")
    @NotBlank(message = "common.client.requiredField")
    private String email;

    @NotNull(message = "common.client.requiredField")
    private String password;

    private String firstName;

    private String lastName;
    
    private Boolean registerAsAdmin;
}
