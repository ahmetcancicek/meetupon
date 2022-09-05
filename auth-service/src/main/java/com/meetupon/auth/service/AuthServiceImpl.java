package com.meetupon.auth.service;

import com.meetupon.auth.dto.LoginRequest;
import com.meetupon.auth.dto.LoginResponse;
import com.meetupon.auth.dto.RegistrationRequest;
import com.meetupon.auth.dto.RegistrationResponse;
import com.meetupon.auth.exception.AuthApiBusinessException;
import com.meetupon.auth.security.KeycloakProvider;
import com.meetupon.auth.vo.RoleName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final KeycloakProvider keycloakProvider;


    public RegistrationResponse registerUser(RegistrationRequest registrationRequest) {
        String newRegistrationRequestEmail = registrationRequest.getEmail();
        if (isEmailAlreadyExists(newRegistrationRequestEmail)) {
            log.error("Email already exists: [{}]", newRegistrationRequestEmail);
            throw new AuthApiBusinessException("auth-service.user.existsEmail");
        }

        String newRegistrationUsername = registrationRequest.getUsername();
        if (isUsernameAlreadyExists(newRegistrationUsername)) {
            log.error("Username already exists: [{}]", newRegistrationUsername);
            throw new AuthApiBusinessException("auth-service.user.existsUsername");
        }


        RegistrationResponse registrationResponse = keycloakProvider.createUser(registrationRequest);
        log.info("Created new user : [{}]", registrationResponse.toString());

        if (registrationRequest.getRegisterAsAdmin())
            keycloakProvider.addToRoleToUser(registrationResponse.getId(), RoleName.ROLE_ADMIN);
        keycloakProvider.addToRoleToUser(registrationResponse.getId(), RoleName.ROLE_USER);

        return registrationResponse;
    }

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        return keycloakProvider.authenticate(loginRequest);
    }


    public Boolean isEmailAlreadyExists(String email) {
        return keycloakProvider.existsByEmail(email);
    }


    public Boolean isUsernameAlreadyExists(String username) {
        return keycloakProvider.existsByUsername(username);
    }
}
