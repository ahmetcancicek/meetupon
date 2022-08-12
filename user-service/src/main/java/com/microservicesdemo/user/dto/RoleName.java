package com.microservicesdemo.user.dto;

public enum RoleName {
    ROLE_USER("USER"), ROLE_ADMIN("ADMIN");

    public final String name;

    RoleName(String name) {
        this.name = name;
    }
}
