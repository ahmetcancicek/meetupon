package com.meetupon.ticket.converter;

import com.meetupon.ticket.dto.MeetupRequest;
import com.meetupon.ticket.model.Meetup;
import com.meetupon.ticket.dto.MeetupResponse;
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
