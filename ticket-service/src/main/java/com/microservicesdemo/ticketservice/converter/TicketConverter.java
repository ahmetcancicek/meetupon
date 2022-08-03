package com.microservicesdemo.ticketservice.converter;

import com.microservicesdemo.ticketservice.dto.TicketRequest;
import com.microservicesdemo.ticketservice.dto.TicketResponse;
import com.microservicesdemo.ticketservice.model.Meetup;
import com.microservicesdemo.ticketservice.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketConverter {

    public Ticket toTicket(TicketRequest ticketRequest) {
        return Ticket.builder()
                .meetup(Meetup.builder().id(ticketRequest.getMeetupId()).build())
                .accountId(ticketRequest.getAccountId())
                .count(ticketRequest.getCount())
                .build();
    }

    public TicketResponse fromTicket(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .accountId(ticket.getAccountId())
                .reserveDate(ticket.getReserveDate())
                .meetupId(ticket.getMeetup().getId())
                .count(ticket.getCount())
                .currency(ticket.getCurrency())
                .price(ticket.getPrice())
                .build();
    }
}
