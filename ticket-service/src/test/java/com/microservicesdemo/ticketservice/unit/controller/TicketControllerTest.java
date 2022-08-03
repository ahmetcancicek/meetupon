package com.microservicesdemo.ticketservice.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicesdemo.ticketservice.controller.TicketController;
import com.microservicesdemo.ticketservice.converter.TicketConverter;
import com.microservicesdemo.ticketservice.dto.Currency;
import com.microservicesdemo.ticketservice.dto.TicketRequest;
import com.microservicesdemo.ticketservice.dto.TicketResponse;
import com.microservicesdemo.ticketservice.model.Meetup;
import com.microservicesdemo.ticketservice.model.Ticket;
import com.microservicesdemo.ticketservice.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void givenIsValidTicket_whenCreateToken_thenCreateTicket() throws Exception {
        // given
        given(ticketService.createTicket(any())).willReturn(Optional.ofNullable(ticketResponse));

        // when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/v1/tickets")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(ticketRequest));

        // then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ticket.getId()))
                .andExpect(jsonPath("$.meetupId").value(ticket.getMeetup().getId()))
                .andExpect(jsonPath("$.accountId").value(ticket.getAccountId()))
                .andExpect(jsonPath("$.count").value(ticket.getCount()))
                .andExpect(jsonPath("$.reserveDate").isNotEmpty())
                .andExpect(jsonPath("$.currency").value(ticket.getCurrency().toString()))
                .andExpect(jsonPath("$.price").value(ticket.getPrice()));
    }
}

