package com.meetupon.auth.service;

import com.meetupon.auth.dto.LoginRequest;
import com.meetupon.auth.dto.LoginResponse;
import com.meetupon.auth.dto.RegistrationRequest;
import com.meetupon.auth.dto.RegistrationResponse;
import com.meetupon.auth.exception.AuthApiBusinessException;
import com.meetupon.auth.security.KeycloakProvider;
import com.meetupon.auth.vo.RoleName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final KeycloakProvider keycloakProvider;

    public AuthServiceImpl(KeycloakProvider keycloakProvider) {
        this.keycloakProvider = keycloakProvider;
    }

    @Override
    public RegistrationResponse registerUser(RegistrationRequest registrationRequest) {
        String email = registrationRequest.getEmail();

        // Check if user has already registers
        checkIfUserAlreadyExists(registrationRequest, email);

        // Create user
        log.info("Trying to save new user [{}]", registrationRequest);
        RegistrationResponse registrationResponse = keycloakProvider.createUser(registrationRequest);
        log.info("Created new user : [{}]", registrationResponse.toString());

        // Add user role
        keycloakProvider.addToRoleToUser(registrationResponse.getId(), RoleName.ROLE_USER);

        // Add admin role
        if (registrationRequest.getRegisterAsAdmin())
            keycloakProvider.addToRoleToUser(registrationResponse.getId(), RoleName.ROLE_ADMIN);

        return registrationResponse;
    }

    @Override
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        return keycloakProvider.authenticate(loginRequest);
    }


    @Override
    public boolean isEmailAlreadyExists(String email) {
        return keycloakProvider.existsByEmail(email);
    }


    @Override
    public boolean isUsernameAlreadyExists(String username) {
        return keycloakProvider.existsByUsername(username);
    }

    private void checkIfUserAlreadyExists(RegistrationRequest registrationRequest, String newRegistrationRequestEmail) {
        if (isEmailAlreadyExists(newRegistrationRequestEmail)) {
            log.error("Email already exists: [{}]", newRegistrationRequestEmail);
            throw new AuthApiBusinessException("auth-service.user.existsEmail");
        }

        String newRegistrationUsername = registrationRequest.getUsername();
        if (isUsernameAlreadyExists(newRegistrationUsername)) {
            log.error("Username already exists: [{}]", newRegistrationUsername);
            throw new AuthApiBusinessException("auth-service.user.existsUsername");
        }
    }
}
