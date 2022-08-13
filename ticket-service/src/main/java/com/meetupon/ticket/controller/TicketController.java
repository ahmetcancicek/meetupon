package com.meetupon.ticket.controller;

import com.meetupon.ticket.exception.TicketCreateException;
import com.meetupon.ticket.service.TicketService;
import com.meetupon.ticket.dto.TicketRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity reserveTicket(@Valid @RequestBody TicketRequest ticketRequest) {
        return ticketService.reserve(ticketRequest).map(ticketResponse -> {
            log.info("Ticket created successfully [{}]", ticketResponse.toString());
            return ResponseEntity.ok(ticketResponse);
        }).orElseThrow(() -> new TicketCreateException(ticketRequest.toString(), ""));
    }
}
