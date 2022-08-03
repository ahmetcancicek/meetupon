package com.microservicesdemo.ticketservice.unit.service;

import com.microservicesdemo.ticketservice.converter.TicketConverter;
import com.microservicesdemo.ticketservice.dto.TicketRequest;
import com.microservicesdemo.ticketservice.dto.TicketResponse;
import com.microservicesdemo.ticketservice.model.Meetup;
import com.microservicesdemo.ticketservice.model.Ticket;
import com.microservicesdemo.ticketservice.repository.TicketRepository;
import com.microservicesdemo.ticketservice.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    TicketRepository ticketRepository;

    @Mock
    TicketConverter ticketConverter;

    @InjectMocks
    TicketService ticketService;

    Ticket ticket;

    TicketRequest ticketRequest;
    TicketResponse ticketResponse;

    @BeforeEach
    void setUp() {
        Meetup meetup = Meetup.builder()
                .id(UUID.randomUUID().toString())
                .eventDate(LocalDateTime.from(LocalDateTime.now().plusDays(10)))
                .name("Encryption Security Session")
                .price(BigDecimal.TEN)
                .url("http://www.zoom.us/A90A2890N")
                .build();

        ticket = Ticket.builder()
                .id(UUID.randomUUID().toString())
                .accountId(UUID.randomUUID().toString())
                .count(1)
                .price(BigDecimal.TEN)
                .meetup(meetup)
                .build();

        ticketRequest = TicketRequest.builder()
                .count(ticket.getCount())
                .accountId(ticket.getAccountId())
                .meetupId(meetup.getId())
                .build();

        ticketResponse = TicketResponse.builder()
                .accountId(ticket.getAccountId())
                .count(ticket.getCount())
                .accountId(ticket.getAccountId())
                .meetupId(meetup.getId())
                .build();
    }

    @Test
    public void givenIsValidTicket_whenCreateToken_thenCreateTicket() {
        // given
        given(ticketRepository.save(any())).willReturn(ticket);
        given(ticketConverter.toTicket(ticketRequest)).willReturn(ticket);
        given(ticketConverter.fromTicket(ticket)).willReturn(ticketResponse);

        // when
        Optional<TicketResponse> expectedTicket = ticketService.createTicket(ticketRequest);

        // then
        verify(ticketRepository, times(1)).save(any());
        assertTrue(expectedTicket.isPresent(), "Object must not be null");
    }


}

