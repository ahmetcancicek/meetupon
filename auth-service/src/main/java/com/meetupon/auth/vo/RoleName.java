package com.meetupon.auth.vo;

public enum RoleName {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private final String name;

    RoleName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
