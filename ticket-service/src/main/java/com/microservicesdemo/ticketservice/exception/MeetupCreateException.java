package com.microservicesdemo.ticketservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class MeetupCreateException extends RuntimeException {

    private final String meetup;

    private final String message;

    public MeetupCreateException(String meetup, String message) {
        super(String.format("Failed to create Meetup[%s] : '%s'", meetup, message));
        this.meetup = meetup;
        this.message = message;
    }
}
