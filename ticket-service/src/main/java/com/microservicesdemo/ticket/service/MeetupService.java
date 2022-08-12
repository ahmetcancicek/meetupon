package com.microservicesdemo.ticket.service;

import com.microservicesdemo.ticket.exception.ResourceNotFoundException;
import com.microservicesdemo.ticket.model.Meetup;
import com.microservicesdemo.ticket.repository.MeetupRepository;
import com.microservicesdemo.ticket.converter.MeetupConverter;
import com.microservicesdemo.ticket.dto.MeetupRequest;
import com.microservicesdemo.ticket.dto.MeetupResponse;
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

    public Optional<MeetupResponse> createMeetup(String userId, MeetupRequest meetupRequest) {
        log.info("Trying to create new meetup: [{}]:", meetupRequest.toString());
        Meetup meetup = meetupConverter.toMeetup(meetupRequest);
        meetup.setUserId(userId);
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
