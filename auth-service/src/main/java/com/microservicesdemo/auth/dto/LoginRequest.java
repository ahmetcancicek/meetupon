package com.microservicesdemo.auth.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class LoginRequest {

    @NotBlank
    private String username;

    @NotEmpty
    private String password;
}
