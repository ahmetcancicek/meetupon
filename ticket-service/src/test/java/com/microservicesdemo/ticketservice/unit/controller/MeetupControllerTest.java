package com.microservicesdemo.ticketservice.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicesdemo.ticket.controller.MeetupController;
import com.microservicesdemo.ticket.dto.Currency;
import com.microservicesdemo.ticket.dto.MeetupRequest;
import com.microservicesdemo.ticket.dto.MeetupResponse;
import com.microservicesdemo.ticket.model.Meetup;
import com.microservicesdemo.ticket.service.MeetupService;
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

@WebMvcTest(MeetupController.class)
@AutoConfigureMockMvc
public class MeetupControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper;

    @MockBean
    protected MeetupService meetupService;

    private Meetup meetup;
    private MeetupResponse meetupResponse;
    private MeetupRequest meetupRequest;

    @BeforeEach
    void setUp() {
        meetup = Meetup.builder()
                .id(UUID.randomUUID().toString())
                .eventDate(LocalDateTime.from(LocalDateTime.now().plusDays(10)))
                .name("Encryption Security Session")
                .currency(Currency.USD)
                .price(BigDecimal.TEN)
                .url("http://www.zoom.us/A90A2890N")
                .build();

        meetupRequest = MeetupRequest.builder()
                .eventDate(meetup.getEventDate())
                .name("Encryption Security Session")
                .currency(Currency.USD)
                .price(BigDecimal.TEN)
                .url("http://www.zoom.us/A90A2890N")
                .build();

        meetupResponse = MeetupResponse.builder()
                .id(meetup.getId())
                .eventDate(meetup.getEventDate())
                .name("Encryption Security Session")
                .currency(Currency.USD)
                .price(BigDecimal.TEN)
                .url("http://www.zoom.us/A90A2890N")
                .build();
    }

    @Test
    public void givenIsValidMeetup_whenCreateMeetup_thenCreateMeetup() throws Exception {
        // given
        given(meetupService.createMeetup(any())).willReturn(Optional.ofNullable(meetupResponse));

        // when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/v1/meetups")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(meetupRequest));

        // then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(meetup.getId()))
                .andExpect(jsonPath("$.name").value(meetup.getName()))
                .andExpect(jsonPath("$.currency").value(meetup.getCurrency().toString()))
                .andExpect(jsonPath("$.url").value(meetup.getUrl()));
    }
}
