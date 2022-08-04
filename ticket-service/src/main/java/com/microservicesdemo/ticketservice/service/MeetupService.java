package com.microservicesdemo.ticketservice.service;

import com.microservicesdemo.ticketservice.converter.MeetupConverter;
import com.microservicesdemo.ticketservice.dto.MeetupRequest;
import com.microservicesdemo.ticketservice.dto.MeetupResponse;
import com.microservicesdemo.ticketservice.exception.ResourceNotFoundException;
import com.microservicesdemo.ticketservice.model.Meetup;
import com.microservicesdemo.ticketservice.repository.MeetupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetupService {

    private final MeetupRepository meetupRepository;

    private final MeetupConverter meetupConverter;

    /**
     *
     * @param meetupRequest
     * @return
     */
    public Optional<MeetupResponse> createMeetup(MeetupRequest meetupRequest) {
        log.info("Trying to create new meetup: [{}]:", meetupRequest.toString());
        Meetup meetup = meetupConverter.toMeetup(meetupRequest);
        meetupRepository.save(meetup);
        log.info("Created new meetup and saved to db: [{}]", meetup.toString());
        return Optional.ofNullable(meetupConverter.fromMeetup(meetup));
    }

    /**
     *
     * @param meetupId
     * @return
     */
    public Optional<MeetupResponse> getMeetup(String meetupId) {
        return meetupRepository.findById(meetupId)
                .map(meetup -> {
                    log.info("Trying to get meeetup: [{}]", meetup.toString());
                    return Optional.ofNullable(meetupConverter.fromMeetup(meetup));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Meetup", "Meetup Id", meetupId));
    }
}
