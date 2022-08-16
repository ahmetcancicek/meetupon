package com.meetupon.auth.service;

import com.meetupon.auth.dto.LoginRequest;
import com.meetupon.auth.dto.LoginResponse;
import com.meetupon.auth.dto.RegistrationRequest;
import com.meetupon.auth.dto.RegistrationResponse;
import com.meetupon.auth.exception.AuthServiceBusinessException;
import com.meetupon.auth.mapper.UserRepresentationMapper;
import com.meetupon.auth.security.KeycloakProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final KeycloakProvider keycloakProvider;

    /**
     *
     * @param registrationRequest
     * @return
     */
    public Optional<RegistrationResponse> registerUser(RegistrationRequest registrationRequest) {
        String newRegistrationRequestEmail = registrationRequest.getEmail();
        if (isEmailAlreadyExists(newRegistrationRequestEmail)) {
            log.error("Email already exists: [{}]", newRegistrationRequestEmail);
            throw new AuthServiceBusinessException("auth-service.user.existsEmail");
        }

        String newRegistrationUsername = registrationRequest.getUsername();
        if (isUsernameAlreadyExists(newRegistrationUsername)) {
            log.error("Username already exists: [{}]", newRegistrationUsername);
            throw new AuthServiceBusinessException("auth-service.user.existsUsername");
        }

        return keycloakProvider.createUser(registrationRequest).map(userRepresentation -> {
            RegistrationResponse registrationResponse = UserRepresentationMapper.INSTANCE.fromUserRepresentation(userRepresentation);
            log.info("Created new user : [{}]", registrationResponse.toString());
            return Optional.of(registrationResponse);
        }).orElseThrow(() -> new AuthServiceBusinessException("The user could not created"));
        // TODO: Do implementation of using i18n
    }

    /**
     *
     * @param loginRequest
     * @return
     */
    public Optional<LoginResponse> authenticateUser(LoginRequest loginRequest) {
        return keycloakProvider.authenticate(loginRequest).map(accessTokenResponse -> {
            log.info("Logged in User returned [API]: [{}]", loginRequest.getUsername());
            return Optional.ofNullable(LoginResponse.builder()
                    .accessToken(accessTokenResponse.getToken())
                    .refreshToken(accessTokenResponse.getRefreshToken())
                    .expiresIn(accessTokenResponse.getExpiresIn())
                    .refreshExpiresIn(accessTokenResponse.getRefreshExpiresIn())
                    .tokenType(accessTokenResponse.getTokenType())
                    .build());
        }).orElseThrow(() -> new AuthServiceBusinessException("Couldn't create access and refresh token for: [{" + loginRequest.toString() + "]"));
        // TODO: Do Implementation of using i18n
    }

    /**
     *
     * @param email
     * @return
     */
    public Boolean isEmailAlreadyExists(String email) {
        return keycloakProvider.existsByEmail(email);
    }

    /**
     *
     * @param username
     * @return
     */
    public Boolean isUsernameAlreadyExists(String username) {
        return keycloakProvider.existsByUsername(username);
    }
}
