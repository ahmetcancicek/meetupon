package com.meetupon.ticket.controller;

import com.meetupon.ticket.dto.MeetupRequest;
import com.meetupon.ticket.service.MeetupServiceImpl;
import com.meetupon.ticket.exception.MeetupCreateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/v1/meetups")
@RequiredArgsConstructor
public class MeetupController {

    private final MeetupServiceImpl meetupService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity createMeetup(Principal principal, @Valid @RequestBody MeetupRequest meetupRequest) {
        return meetupService.createMeetup(principal.getName(), meetupRequest).map(meetupResponse -> {
            log.info("Meetup created successfully [{}]", meetupResponse.toString());
            return ResponseEntity.ok(meetupResponse);
        }).orElseThrow(() -> new MeetupCreateException(meetupRequest.toString(), ""));
    }
}
