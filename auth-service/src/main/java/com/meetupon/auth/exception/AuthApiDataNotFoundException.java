package com.meetupon.auth.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuthApiDataNotFoundException extends RuntimeException {
    private final String key;
    private final String[] args;

    public AuthApiDataNotFoundException(String key) {
        super(key);
        this.key = key;
        args = new String[0];
    }

    public AuthApiDataNotFoundException(String key, String... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
}
