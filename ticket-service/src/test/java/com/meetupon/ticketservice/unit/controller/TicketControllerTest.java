package com.meetupon.ticketservice.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetupon.ticket.controller.TicketController;
import com.meetupon.ticket.dto.Currency;
import com.meetupon.ticket.dto.TicketRequest;
import com.meetupon.ticket.dto.TicketResponse;
import com.meetupon.ticket.model.Meetup;
import com.meetupon.ticket.model.Ticket;
import com.meetupon.ticket.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TicketController.class)
@AutoConfigureMockMvc
public class TicketControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper;

    @MockBean
    protected TicketService ticketService;

    private Ticket ticket;
    private TicketResponse ticketResponse;
    private TicketRequest ticketRequest;

    @BeforeEach
    void setUp() {
        Meetup meetup = Meetup.builder()
                .id(UUID.randomUUID().toString())
                .eventDate(LocalDateTime.from(LocalDateTime.now().plusDays(10)))
                .name("Encryption Security Session")
                .currency(Currency.USD)
                .price(BigDecimal.TEN)
                .url("http://www.zoom.us/A90A2890N")
                .build();

        ticket = Ticket.builder()
                .id(UUID.randomUUID().toString())
                .accountId(UUID.randomUUID().toString())
                .count(1)
                .price(BigDecimal.TEN)
                .currency(meetup.getCurrency())
                .reserveDate(LocalDateTime.now())
                .meetup(meetup)
                .build();

        ticketRequest = TicketRequest.builder()
                .count(ticket.getCount())
                .accountId(ticket.getAccountId())
                .meetupId(meetup.getId())
                .build();

        ticketResponse = TicketResponse.builder()
                .id(ticket.getId())
                .count(ticket.getCount())
                .accountId(ticket.getAccountId())
                .reserveDate(ticket.getReserveDate())
                .meetupId(meetup.getId())
                .price(ticket.getPrice())
                .currency(ticket.getCurrency())
                .build();
    }

    @Test
    public void givenIsValidTicket_whenReserveTicket_thenReserveTicket() throws Exception {

    }
}

