package com.microservicesdemo.ticketservice.integration.repository;

import com.microservicesdemo.ticket.model.Meetup;
import com.microservicesdemo.ticket.repository.MeetupRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeetupRepositoryIT extends AbstractIT {
    @Autowired
    private MeetupRepository meetupRepository;

    @Test
    public void shouldSaveMeetup() {
        Meetup meetup = Meetup.builder()
                .eventDate(LocalDateTime.from(LocalDateTime.now().plusDays(10)))
                .name("Encryption Security Session")
                .price(BigDecimal.TEN)
                .url("http://www.zoom.us/A90A2890N")
                .build();

        Meetup createdMeetup = meetupRepository.save(meetup);
        Meetup expectedMeetup = testEntityManager.find(Meetup.class, createdMeetup.getId());

        assertEquals(meetup, expectedMeetup, "Objects must be identical");
    }
}
