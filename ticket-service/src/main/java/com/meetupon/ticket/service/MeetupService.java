package com.meetupon.ticket.service;

import com.meetupon.ticket.dto.MeetupRequest;
import com.meetupon.ticket.dto.MeetupResponse;

import java.util.Optional;

public interface MeetupService {
    Optional<MeetupResponse> createMeetup(MeetupRequest meetupRequest);

    Optional<MeetupResponse> createMeetup(String userId, MeetupRequest meetupRequest);

    Optional<MeetupResponse> getMeetup(String meetupId);
}
