package com.meetupon.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuthServiceDataNotFoundException extends RuntimeException {
    public AuthServiceDataNotFoundException(String message) {
        super(message);
    }
}
