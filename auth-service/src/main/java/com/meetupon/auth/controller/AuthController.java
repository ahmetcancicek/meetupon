package com.meetupon.auth.controller;

import com.meetupon.auth.dto.RegistrationRequest;
import com.meetupon.auth.exception.AuthServiceException;
import com.meetupon.auth.dto.LoginRequest;
import com.meetupon.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return authService.registerUser(registrationRequest).map(registrationResponse -> {
            log.info("User added successfully [{}]", registrationResponse.toString());
            return ResponseEntity.ok(registrationResponse);
        }).orElseThrow(() -> new AuthServiceException("User could not created"));
    }

    @PostMapping("/login")
    public ResponseEntity authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest)
                .map(loginResponse -> {
                    log.info("User authenticate successfully [{}]", loginResponse.toString());
                    return ResponseEntity.ok(loginResponse);
                }).orElseThrow(() -> new AuthServiceException(""));
    }
}
