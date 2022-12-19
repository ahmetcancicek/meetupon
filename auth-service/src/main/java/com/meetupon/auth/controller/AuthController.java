package com.meetupon.auth.controller;

import com.meetupon.auth.advice.ApiResponse;
import com.meetupon.auth.dto.LoginResponse;
import com.meetupon.auth.dto.RegistrationRequest;
import com.meetupon.auth.dto.LoginRequest;
import com.meetupon.auth.dto.RegistrationResponse;
import com.meetupon.auth.service.AuthService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController extends BaseController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

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
