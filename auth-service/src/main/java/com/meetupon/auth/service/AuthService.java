package com.meetupon.auth.service;

import com.meetupon.auth.dto.LoginRequest;
import com.meetupon.auth.dto.LoginResponse;
import com.meetupon.auth.dto.RegistrationRequest;
import com.meetupon.auth.dto.RegistrationResponse;
import com.meetupon.auth.exception.AuthServiceException;
import com.microservicesdemo.auth.dto.*;
import com.meetupon.auth.mapper.UserRepresentationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final KeycloakService keycloakService;

    public Optional<RegistrationResponse> registerUser(RegistrationRequest registrationRequest) {
        // TODO: Check if email already exists
        String newRegistrationRequestEmail = registrationRequest.getEmail();
        if (isEmailAlreadyExists(newRegistrationRequestEmail)) {
            log.error("Email already exists: [{}]", newRegistrationRequestEmail);
            throw new AuthServiceException("Email already exists");
        }

        // TODO: Check if username already exists
        String newRegistrationUsername = registrationRequest.getUsername();
        if (isUsernameAlreadyExists(newRegistrationUsername)) {
            log.error("Username already exists: [{}]", newRegistrationUsername);
            throw new AuthServiceException("Username already exists");
        }

        return keycloakService.createUser(registrationRequest).map(userRepresentation -> {
            RegistrationResponse registrationResponse = UserRepresentationMapper.INSTANCE.fromUserRepresentation(userRepresentation);
            log.info("Created new user : [{}]", registrationResponse.toString());
            return Optional.of(registrationResponse);
        }).orElseThrow(() -> new AuthServiceException("The user could not created"));
    }

    public Optional<LoginResponse> authenticateUser(LoginRequest loginRequest) {
        return keycloakService.generateToken(loginRequest.getUsername(), loginRequest.getPassword()).map(accessTokenResponse -> {
            log.info("Logged in User returned [API]: [{}]", loginRequest.getUsername());
            return Optional.ofNullable(LoginResponse.builder()
                    .accessToken(accessTokenResponse.getToken())
                    .refreshToken(accessTokenResponse.getRefreshToken())
                    .expiresIn(accessTokenResponse.getExpiresIn())
                    .refreshExpiresIn(accessTokenResponse.getRefreshExpiresIn())
                    .build());
        }).orElseThrow(() -> new AuthServiceException("Couldn't create refresh token for: [" + loginRequest.toString() + "]"));
    }


    public Boolean isEmailAlreadyExists(String email) {
        return false;
    }

    public Boolean isUsernameAlreadyExists(String username) {
        return false;
    }

}
