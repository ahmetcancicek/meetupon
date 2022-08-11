package com.microservicesdemo.ticket.controller;

import com.microservicesdemo.ticket.exception.MeetupCreateException;
import com.microservicesdemo.ticket.dto.MeetupRequest;
import com.microservicesdemo.ticket.service.MeetupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/meetups")
@RequiredArgsConstructor
public class MeetupController {

    private final MeetupService meetupService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity createMeetup(@Valid @RequestBody MeetupRequest meetupRequest) {
        return meetupService.createMeetup(meetupRequest).map(meetupResponse -> {
            log.info("Meetup created successfully [{}]", meetupResponse.toString());
            return ResponseEntity.ok(meetupResponse);
        }).orElseThrow(() -> new MeetupCreateException(meetupRequest.toString(), ""));
    }
}
