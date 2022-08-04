package com.microservicesdemo.ticketservice.controller;

import com.microservicesdemo.ticketservice.dto.MeetupRequest;
import com.microservicesdemo.ticketservice.exception.MeetupCreateException;
import com.microservicesdemo.ticketservice.service.MeetupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/meetups")
@RequiredArgsConstructor
public class MeetupController {

    private final MeetupService meetupService;

    @PostMapping
    public ResponseEntity createMeetup(@Valid @RequestBody MeetupRequest meetupRequest) {
        return meetupService.createMeetup(meetupRequest).map(meetupResponse -> {
            log.info("Meetup created successfully [{}]", meetupResponse.toString());
            return ResponseEntity.ok(meetupResponse);
        }).orElseThrow(() -> new MeetupCreateException(meetupRequest.toString(), ""));
    }
}
