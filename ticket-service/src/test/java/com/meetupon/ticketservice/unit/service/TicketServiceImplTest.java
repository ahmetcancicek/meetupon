package com.meetupon.ticketservice.unit.service;

import com.meetupon.ticket.converter.TicketConverter;
import com.meetupon.ticket.dto.MeetupResponse;
import com.meetupon.ticket.dto.TicketRequest;
import com.meetupon.ticket.dto.TicketResponse;
import com.meetupon.ticket.model.Meetup;
import com.meetupon.ticket.model.Ticket;
import com.meetupon.ticket.repository.TicketRepository;
import com.meetupon.ticket.service.MeetupServiceImpl;
import com.meetupon.ticket.service.TicketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
class TicketServiceImplTest {

    @Mock
    TicketRepository ticketRepository;

    @Mock
    TicketConverter ticketConverter;

    @InjectMocks
    TicketServiceImpl ticketService;

    @Mock
    MeetupServiceImpl meetupService;

    Ticket ticket;
    TicketRequest ticketRequest;
    TicketResponse ticketResponse;


    Meetup meetup;

    MeetupResponse meetupResponse;

    @BeforeEach
    void setUp() {
        meetup = Meetup.builder()
                .id(UUID.randomUUID().toString())
                .eventDate(LocalDateTime.from(LocalDateTime.now().plusDays(10)))
                .name("Encryption Security Session")
                .price(BigDecimal.TEN)
                .url("http://www.zoom.us/A90A2890N")
                .build();

        meetupResponse = MeetupResponse.builder()
                .id(meetup.getId())
                .eventDate(meetup.getEventDate())
                .name(meetup.getName())
                .price(meetup.getPrice())
                .url(meetup.getUrl())
                .currency(meetup.getCurrency())
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
    public void givenIsValidTicket_whenCreateTicket_thenCreateTicket() {
        // given
        given(ticketRepository.save(any())).willReturn(ticket);
        given(ticketConverter.toTicket(ticketRequest)).willReturn(ticket);
        given(ticketConverter.fromTicket(ticket)).willReturn(ticketResponse);
        given(meetupService.getMeetup(any())).willReturn(Optional.ofNullable(meetupResponse));

        // when
        Optional<TicketResponse> expectedTicket = ticketService.createTicket(ticketRequest);

        // then
        verify(ticketRepository, times(1)).save(any());
        assertTrue(expectedTicket.isPresent(), "Object must not be null");
    }


    @Test
    public void givenTicketWithHaveCountMoreThan_whenCreateTicket_thenCreateTicket() {
        // given
        given(ticketRepository.save(any())).willReturn(ticket);
        given(ticketConverter.toTicket(ticketRequest)).willReturn(ticket);
        given(ticketConverter.fromTicket(ticket)).willReturn(ticketResponse);
        given(meetupService.getMeetup(any())).willReturn(Optional.ofNullable(meetupResponse));

        ticket.setCount(5);

        // when
        Optional<TicketResponse> expectedTicket = ticketService.createTicket(ticketRequest);

        // then
        verify(ticketRepository, times(1)).save(any());
        assertTrue(expectedTicket.isPresent(), "Object must not be null");
    }
}

