package com.meetupon.ticket.converter;

import com.meetupon.ticket.model.Meetup;
import com.meetupon.ticket.model.Ticket;
import com.meetupon.ticket.dto.TicketRequest;
import com.meetupon.ticket.dto.TicketResponse;
import org.springframework.stereotype.Component;

@Component
public class TicketConverter {

    public Ticket toTicket(TicketRequest ticketRequest) {
        return Ticket.builder()
                .meetup(Meetup.builder().id(ticketRequest.getMeetupId()).build())
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
