package com.microservicesdemo.ticket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class TicketCreateException extends RuntimeException {

    private final String ticket;
    private final String message;

    public TicketCreateException(String ticket, String message) {
        super(String.format("Failed to create Ticket[%s] : '%s'", ticket, message));
        this.ticket = ticket;
        this.message = message;
    }
}
