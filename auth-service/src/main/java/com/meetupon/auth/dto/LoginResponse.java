package com.meetupon.auth.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class LoginResponse {
    protected String accessToken;
    protected String refreshToken;
    protected long expiresIn;
    protected long refreshExpiresIn;
}
