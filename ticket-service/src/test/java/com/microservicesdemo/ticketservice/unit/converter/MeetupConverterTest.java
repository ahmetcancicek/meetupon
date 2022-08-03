package com.microservicesdemo.ticketservice.unit.converter;

import com.microservicesdemo.ticketservice.converter.MeetupConverter;
import com.microservicesdemo.ticketservice.dto.Currency;
import com.microservicesdemo.ticketservice.dto.MeetupRequest;
import com.microservicesdemo.ticketservice.dto.MeetupResponse;
import com.microservicesdemo.ticketservice.model.Meetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MeetupConverterTest {

    private MeetupConverter meetupConverter;

    @BeforeEach
    void setUp() {
        meetupConverter = new MeetupConverter();
    }

    @Test
    public void whenFromMeetupRequest_thenReturnMeetup() {
        MeetupRequest meetupRequest = MeetupRequest.builder()
                .name("Encryption Security Session")
                .price(BigDecimal.TEN)
                .eventDate(LocalDateTime.now().plusDays(10))
                .currency(Currency.USD)
                .url("http://www.zoom.us/A90A2890N")
                .build();

        Meetup meetup = meetupConverter.toMeetup(meetupRequest);

        assertEquals(meetupRequest.getName(), meetup.getName());
        assertEquals(meetupRequest.getPrice(), meetup.getPrice());
        assertEquals(meetupRequest.getEventDate(), meetup.getEventDate());
        assertEquals(meetupRequest.getCurrency(), meetup.getCurrency());
        assertEquals(meetupRequest.getUrl(), meetup.getUrl());
    }

    @Test
    public void whenToMeetup_thenReturnMeetupResponse() {
        Meetup meetup = Meetup.builder()
                .id(UUID.randomUUID().toString())
                .eventDate(LocalDateTime.now().plusDays(10))
                .name("Encryption Security Session")
                .currency(Currency.USD)
                .price(BigDecimal.TEN)
                .url("http://www.zoom.us/A90A2890N")
                .build();

        MeetupResponse meetupResponse = meetupConverter.fromMeetup(meetup);

        assertEquals(meetup.getId(), meetupResponse.getId());
        assertEquals(meetup.getEventDate(), meetupResponse.getEventDate());
        assertEquals(meetup.getName(), meetupResponse.getName());
        assertEquals(meetup.getCurrency(), meetupResponse.getCurrency());
        assertEquals(meetup.getPrice(), meetupResponse.getPrice());
        assertEquals(meetup.getUrl(), meetupResponse.getUrl());
    }
}
