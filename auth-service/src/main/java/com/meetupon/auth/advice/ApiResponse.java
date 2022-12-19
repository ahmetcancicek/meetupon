package com.meetupon.auth.advice;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ErrorResponse getErrors() {
        return errors;
    }

    public void setErrors(ErrorResponse errors) {
        this.errors = errors;
    }
}
