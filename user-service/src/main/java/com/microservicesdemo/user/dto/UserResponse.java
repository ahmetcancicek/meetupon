package com.microservicesdemo.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class UserResponse {
    private String id;
    private String username;
    private Boolean enabled;
    private String firstName;
    private String lastName;
    private String email;
}
