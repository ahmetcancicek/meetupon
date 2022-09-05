package com.meetupon.ticket.service;

import com.meetupon.ticket.dto.TicketRequest;
import com.meetupon.ticket.dto.TicketResponse;

import java.util.Optional;

public interface TicketService {
    Optional<TicketResponse> createTicket(TicketRequest ticketRequest);

    Optional<TicketResponse> reserve(TicketRequest ticketRequest);
}
