package com.microservicesdemo.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UserServiceException extends RuntimeException {
    public UserServiceException(String message) {
        super(message);
    }
}
