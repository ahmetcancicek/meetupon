package com.microservicesdemo.ticketservice.controller;

import com.microservicesdemo.ticketservice.dto.ApiResponse;
import com.microservicesdemo.ticketservice.dto.TicketRequest;
import com.microservicesdemo.ticketservice.exception.TicketCreateException;
import com.microservicesdemo.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity createTicket(@RequestBody TicketRequest ticketRequest) {
        return ticketService.createTicket(ticketRequest).map(ticketResponse -> {
            log.info("Ticket created successfully [{}]", ticketResponse.toString());
            return ResponseEntity.ok(ticketResponse);
        }).orElseThrow(() -> new TicketCreateException(ticketRequest.toString(), ""));
    }
}
