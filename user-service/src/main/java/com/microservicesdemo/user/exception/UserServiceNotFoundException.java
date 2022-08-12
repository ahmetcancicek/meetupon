package com.microservicesdemo.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserServiceNotFoundException extends RuntimeException {
    public UserServiceNotFoundException(String message) {
        super(message);
    }
}
