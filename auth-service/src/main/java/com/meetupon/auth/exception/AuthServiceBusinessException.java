package com.meetupon.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class AuthServiceBusinessException extends RuntimeException {
    public AuthServiceBusinessException(String message) {
        super(message);
    }
}
