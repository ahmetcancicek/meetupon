package com.meetupon.auth.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class RegistrationResponse {

    private String id;

    private String username;

    private String firstName;

    private String lastName;
    
    private String email;
}
