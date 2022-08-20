package com.meetupon.auth.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class AuthApiBusinessException extends RuntimeException {

    private final String key;
    private final String[] args;

    public AuthApiBusinessException(String key) {
        super(key);
        this.key = key;
        args = new String[0];
    }

    public AuthApiBusinessException(String key, String... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
}
