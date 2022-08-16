package com.meetupon.auth.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class LoginResponse {

    String accessToken;

    String refreshToken;

    long expiresIn;

    long refreshExpiresIn;
    
    String tokenType;
}
