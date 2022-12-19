package com.meetupon.auth.controller;

import com.meetupon.auth.advice.ApiResponse;
import com.meetupon.auth.advice.ErrorResponse;
import com.meetupon.auth.advice.ResponseBuilder;

public class BaseController {

    protected <T> ApiResponse<T> respond(T item) {
        return ResponseBuilder.build(item);
    }

    protected ApiResponse<ErrorResponse> respond(ErrorResponse errorResponse) {
        return ResponseBuilder.build(errorResponse);
    }
}
