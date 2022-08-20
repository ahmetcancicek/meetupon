package com.meetupon.auth.controller;

import com.meetupon.auth.common.rest.ApiResponse;
import com.meetupon.auth.common.rest.ErrorResponse;
import com.meetupon.auth.common.rest.ResponseBuilder;

public class BaseController {

    protected <T> ApiResponse<T> respond(T item) {
        return ResponseBuilder.build(item);
    }

    protected ApiResponse<ErrorResponse> respond(ErrorResponse errorResponse) {
        return ResponseBuilder.build(errorResponse);
    }
}
