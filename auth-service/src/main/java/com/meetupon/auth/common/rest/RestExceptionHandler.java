package com.meetupon.auth.common.rest;


import com.meetupon.auth.exception.AuthApiBusinessException;
import com.meetupon.auth.controller.BaseController;
import com.meetupon.auth.exception.AuthApiDataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class RestExceptionHandler extends BaseController {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<ErrorResponse> handleExceptions(Exception exception, Locale locale) {
        log.error("An error occurred!: ", exception);
        return createErrorResponseFromMessageSource("system.error.occurred", locale);
    }

    @ExceptionHandler(AuthApiBusinessException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ApiResponse<ErrorResponse> handleAuthApiBusinessException(AuthApiBusinessException exception, Locale locale) {
        return createErrorResponseFromMessageSource(exception.getKey(), locale, exception.getArgs());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AuthApiDataNotFoundException.class)
    public ApiResponse<ErrorResponse> handleAuthDataNotFoundException(AuthApiDataNotFoundException exception, Locale locale) {
        return createErrorResponseFromMessageSource(exception.getKey(), locale, exception.getArgs());
    }

    private ApiResponse<ErrorResponse> createErrorResponseFromMessageSource(String key, Locale locale, String... args) {
        List<String> messages = retrieveLocalizationMessage(key, locale, args);
        return respond(new ErrorResponse(messages.get(0), messages.get(1)));
    }

    private List<String> retrieveLocalizationMessage(String key, Locale locale, String... args) {
        String message = messageSource.getMessage(key, args, locale);
        return Pattern.compile(";").splitAsStream(message).collect(Collectors.toList());
    }

}
