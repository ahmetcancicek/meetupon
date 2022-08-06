package com.microservicesdemo.ticket.controller;

import com.microservicesdemo.ticket.exception.TicketCreateException;
import com.microservicesdemo.ticket.dto.TicketRequest;
import com.microservicesdemo.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity reserveTicket(@Valid @RequestBody TicketRequest ticketRequest) {
        return ticketService.reserve(ticketRequest).map(ticketResponse -> {
            log.info("Ticket created successfully [{}]", ticketResponse.toString());
            return ResponseEntity.ok(ticketResponse);
        }).orElseThrow(() -> new TicketCreateException(ticketRequest.toString(), ""));
    }
}
