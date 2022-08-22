package com.meetupon.auth.service;

import com.meetupon.auth.dto.LoginRequest;
import com.meetupon.auth.dto.LoginResponse;
import com.meetupon.auth.dto.RegistrationRequest;
import com.meetupon.auth.dto.RegistrationResponse;
import com.meetupon.auth.exception.AuthApiBusinessException;
import com.meetupon.auth.security.KeycloakProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private KeycloakProvider keycloakProvider;
    private RegistrationResponse registrationResponse;

    private LoginResponse loginResponse;

    @BeforeEach
    void setUp() {
        registrationResponse = RegistrationResponse.builder()
                .id(UUID.randomUUID().toString())
                .email("ericdoor@email.com")
                .firstName("Eric")
                .lastName("Door")
                .username("ericdoor")
                .build();


        loginResponse = LoginResponse.builder()
                .accessToken(UUID.randomUUID().toString())
                .refreshToken(UUID.randomUUID().toString())
                .build();
    }

    @Test
    void givenUser_whenRegisterUser_thenReturnUser() {
        // given
        given(keycloakProvider.createUser(any())).willReturn(registrationResponse);
        given(keycloakProvider.existsByEmail(any())).willReturn(false);
        given(keycloakProvider.existsByUsername(any())).willReturn(false);

        // when
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .password("HAT79AJA8")
                .email("ericdoor@email.com")
                .firstName("Eric")
                .lastName("Door")
                .username("ericdoor")
                .registerAsAdmin(false)
                .build();

        RegistrationResponse createdUser = authService.registerUser(registrationRequest);

        // then
        assertEquals(registrationResponse, createdUser);
    }

    @Test
    void givenAlreadyExistsUsername_whenRegisterUser_thenThrowException() {
        // given
        given(keycloakProvider.existsByEmail(any())).willReturn(true);

        // when
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .password("HAT79AJA8")
                .email("ericdoor@email.com")
                .firstName("Eric")
                .lastName("Door")
                .username("ericdoor")
                .registerAsAdmin(false)
                .build();

        Throwable throwable = catchThrowable(() -> {
            authService.registerUser(registrationRequest);
        });

        // then
        assertThat(throwable).isInstanceOf(AuthApiBusinessException.class);
    }

    @Test
    void givenAlreadyExistsEmail_whenRegisterUser_thenThrowException() {
        // given
        given(keycloakProvider.existsByEmail(any())).willReturn(false);
        given(keycloakProvider.existsByUsername(any())).willReturn(true);

        // when
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .password("HAT79AJA8")
                .email("ericdoor@email.com")
                .firstName("Eric")
                .lastName("Door")
                .username("ericdoor")
                .registerAsAdmin(false)
                .build();

        Throwable throwable = catchThrowable(() -> {
            authService.registerUser(registrationRequest);
        });

        // then
        assertThat(throwable).isInstanceOf(AuthApiBusinessException.class);
    }

    @Test
    void givenUser_whenAuthenticateUser_thenReturnNewToken() {
        // given
        given(keycloakProvider.authenticate(any())).willReturn(loginResponse);

        // when
        LoginRequest loginRequest = LoginRequest.builder()
                .password("HAT79AJA8")
                .username("ericdoor")
                .build();

        LoginResponse authenticatedUser = authService.authenticateUser(loginRequest);

        // then
        assertEquals(loginResponse, authenticatedUser);
    }

    @Test
    void givenExistingEmail_whenIsEmailAlreadyExists_thenReturnTrue() {
        // given
        given(keycloakProvider.existsByEmail(any())).willReturn(true);

        // when
        Boolean status = authService.isEmailAlreadyExists(registrationResponse.getEmail());

        // then
        assertTrue(status);
    }

    @Test
    void givenExistingUsername_whenIsUsernameAlreadyExists_thenReturnTrue() {
        // given
        given(keycloakProvider.existsByUsername(any())).willReturn(true);

        // when
        Boolean status = authService.isUsernameAlreadyExists(registrationResponse.getUsername());

        // then
        assertTrue(status);
    }
}