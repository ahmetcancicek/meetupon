package com.meetupon.auth.common.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {

    private T data;

    private ErrorResponse errors;

    public ApiResponse(){

    }

    public ApiResponse(T data){
        this.data = data;
    }

    public ApiResponse(ErrorResponse errors) {
        this.errors = errors;
    }
}
