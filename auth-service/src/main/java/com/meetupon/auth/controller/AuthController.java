package com.meetupon.auth.controller;

import com.meetupon.auth.common.rest.ApiResponse;
import com.meetupon.auth.dto.LoginResponse;
import com.meetupon.auth.dto.RegistrationRequest;
import com.meetupon.auth.dto.LoginRequest;
import com.meetupon.auth.dto.RegistrationResponse;
import com.meetupon.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<RegistrationResponse> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        RegistrationResponse registrationResponse = authService.registerUser(registrationRequest);
        return respond(registrationResponse);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.authenticateUser(loginRequest);
        return respond(loginResponse);
    }
}
