package com.microservicesdemo.ticketservice.converter;

import com.microservicesdemo.ticketservice.dto.MeetupRequest;
import com.microservicesdemo.ticketservice.dto.MeetupResponse;
import com.microservicesdemo.ticketservice.model.Meetup;
import org.springframework.stereotype.Component;

@Component
public class MeetupConverter {
    public Meetup toMeetup(MeetupRequest meetupRequest) {
        return Meetup.builder()
                .name(meetupRequest.getName())
                .price(meetupRequest.getPrice())
                .eventDate(meetupRequest.getEventDate())
                .currency(meetupRequest.getCurrency())
                .url(meetupRequest.getUrl())
                .build();
    }

    public MeetupResponse fromMeetup(Meetup meetup) {
        return MeetupResponse.builder()
                .id(meetup.getId())
                .name(meetup.getName())
                .price(meetup.getPrice())
                .currency(meetup.getCurrency())
                .eventDate(meetup.getEventDate())
                .url(meetup.getUrl())
                .build();
    }
}
