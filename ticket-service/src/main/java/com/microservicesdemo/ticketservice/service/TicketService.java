package com.microservicesdemo.ticketservice.service;

import com.microservicesdemo.ticketservice.converter.TicketConverter;
import com.microservicesdemo.ticketservice.dto.TicketRequest;
import com.microservicesdemo.ticketservice.dto.TicketResponse;
import com.microservicesdemo.ticketservice.exception.ResourceNotFoundException;
import com.microservicesdemo.ticketservice.model.Meetup;
import com.microservicesdemo.ticketservice.model.Ticket;
import com.microservicesdemo.ticketservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketConverter ticketConverter;

    private final MeetupService meetupService;

    public Optional<TicketResponse> createTicket(TicketRequest ticketRequest) {
        log.info("Trying to create new ticket: [{}]", ticketRequest.toString());
        Ticket ticket = ticketConverter.toTicket(ticketRequest);
        ticket.setReserveDate(LocalDateTime.now());

        meetupService.getMeetup(ticketRequest.getMeetupId())
                .map(meetupResponse -> {
                    ticket.setPrice(meetupResponse.getPrice());
                    ticket.setCurrency(meetupResponse.getCurrency());
                    return true;
                }).orElseThrow(() -> new ResourceNotFoundException("Meetup", "Meetup Id", ticketRequest.getMeetupId()));

        ticketRepository.save(ticket);
        log.info("Created new ticket and saved to db: [{}]", ticket.toString());
        return Optional.ofNullable(ticketConverter.fromTicket(ticket));
    }
}
