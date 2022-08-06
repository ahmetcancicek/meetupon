package com.microservicesdemo.ticket.service;

import com.microservicesdemo.ticket.converter.TicketConverter;
import com.microservicesdemo.ticket.exception.ResourceNotFoundException;
import com.microservicesdemo.ticket.model.Ticket;
import com.microservicesdemo.ticket.repository.TicketRepository;
import com.microservicesdemo.ticket.dto.TicketRequest;
import com.microservicesdemo.ticket.dto.TicketResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketConverter ticketConverter;
    private final MeetupService meetupService;

    /**
     * It creates a ticket
     * @param ticketRequest
     * @return
     */
    public Optional<TicketResponse> createTicket(TicketRequest ticketRequest) {
        log.info("Trying to create new ticket: [{}]", ticketRequest.toString());
        Ticket ticket = ticketConverter.toTicket(ticketRequest);
        ticket.setReserveDate(LocalDateTime.now());
        ticket.setPrice(ticket.findTotalPrice());

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

    /**
     * It does operations to do payment and create a reservation
     * @param ticketRequest
     * @return
     */
    public Optional<TicketResponse> reserve(TicketRequest ticketRequest) {
        log.info("Trying to reserve new ticket: [{}]", ticketRequest.toString());
        // *
        // * 1- Get account from account-service
        // * 2- Get meetup from meetup service implementation
        // * 3- Start to new transaction for payment between ticket-service and payment-service
        // * 4- If payment is successful then create and publish ticket
        // * 5- If payment is successful but create is not successful then rollback for payment and publish
        // * 6- If payment is not successful then throw exception

        return null;
    }
}
