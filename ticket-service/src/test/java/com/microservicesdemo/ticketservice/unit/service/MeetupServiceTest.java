package com.microservicesdemo.ticketservice.unit.service;

import com.microservicesdemo.ticketservice.converter.MeetupConverter;
import com.microservicesdemo.ticketservice.dto.MeetupRequest;
import com.microservicesdemo.ticketservice.dto.MeetupResponse;
import com.microservicesdemo.ticketservice.model.Meetup;
import com.microservicesdemo.ticketservice.repository.MeetupRepository;
import com.microservicesdemo.ticketservice.service.MeetupService;
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
public class MeetupServiceTest {

    @Mock
    MeetupRepository meetupRepository;

    @Mock
    MeetupConverter meetupConverter;

    @InjectMocks
    MeetupService meetupService;

    Meetup meetup;

    MeetupRequest meetupRequest;

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

        meetupRequest = MeetupRequest.builder()
                .name("Encryption Security Session")
                .price(BigDecimal.TEN)
                .eventDate(meetup.getEventDate())
                .url(meetup.getUrl())
                .build();

        meetupResponse = MeetupResponse.builder()
                .id(meetup.getId())
                .name("Encryption Security Session")
                .price(BigDecimal.TEN)
                .eventDate(meetup.getEventDate())
                .url(meetup.getUrl())
                .build();
    }

    @Test
    public void givenIsValidMeetup_whenCreateMeetup_thenCreateMeetup() {
        // given
        given(meetupRepository.save(any())).willReturn(meetup);
        given(meetupConverter.toMeetup(meetupRequest)).willReturn(meetup);
        given(meetupConverter.fromMeetup(meetup)).willReturn(meetupResponse);


        // when
        Optional<MeetupResponse> meetupResponse = meetupService.createMeetup(meetupRequest);

        // then
        verify(meetupRepository, times(1)).save(any());
        assertTrue(meetupResponse.isPresent(), "Object must not be null");
    }
}
