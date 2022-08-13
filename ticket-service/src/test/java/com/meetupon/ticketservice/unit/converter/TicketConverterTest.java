package com.meetupon.ticketservice.unit.converter;

import com.meetupon.ticket.converter.TicketConverter;
import com.meetupon.ticket.dto.Currency;
import com.meetupon.ticket.dto.TicketRequest;
import com.meetupon.ticket.dto.TicketResponse;
import com.meetupon.ticket.model.Meetup;
import com.meetupon.ticket.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TicketConverterTest {

    private TicketConverter ticketConverter;

    @BeforeEach
    void setUp() {
        ticketConverter = new TicketConverter();
    }

    @Test
    public void whenFromTicketRequest_thenReturnTicket() {
        TicketRequest ticketRequest = TicketRequest.builder()
                .count(1)
                .accountId(UUID.randomUUID().toString())
                .meetupId(UUID.randomUUID().toString())
                .build();

        Ticket ticket = ticketConverter.toTicket(ticketRequest);

        assertEquals(ticketRequest.getCount(), ticket.getCount());
        assertEquals(ticketRequest.getAccountId(), ticket.getAccountId());
        assertEquals(ticketRequest.getMeetupId(), ticket.getMeetup().getId());
    }

    @Test
    public void whenToTicket_thenReturnTicketResponse() {
        Meetup meetup = Meetup.builder()
                .id(UUID.randomUUID().toString())
                .eventDate(LocalDateTime.now().plusDays(10))
                .name("Encryption Security Session")
                .currency(Currency.USD)
                .price(BigDecimal.TEN)
                .url("http://www.zoom.us/A90A2890N")
                .build();

        Ticket ticket = Ticket.builder()
                .id(UUID.randomUUID().toString())
                .accountId(UUID.randomUUID().toString())
                .count(1)
                .price(BigDecimal.TEN)
                .currency(meetup.getCurrency())
                .reserveDate(LocalDateTime.now())
                .meetup(meetup)
                .build();

        TicketResponse ticketResponse = ticketConverter.fromTicket(ticket);

        assertEquals(ticket.getId(), ticketResponse.getId());
        assertEquals(ticket.getAccountId(), ticketResponse.getAccountId());
        assertEquals(ticket.getCount(), ticketResponse.getCount());
        assertEquals(ticket.getPrice(), ticketResponse.getPrice());
        assertEquals(ticket.getCurrency(), ticketResponse.getCurrency());
        assertEquals(ticket.getReserveDate(), ticketResponse.getReserveDate());
        assertEquals(ticket.getMeetup().getId(), ticketResponse.getMeetupId());
    }

}
