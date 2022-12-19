package com.meetupon.auth.advice;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;
    private String code;
    private String message;

    public ErrorResponse() {
        timestamp = new Date();
    }

    public ErrorResponse(String code, String message) {
        this();
        this.code = code;
        this.message = message;
    }
}
