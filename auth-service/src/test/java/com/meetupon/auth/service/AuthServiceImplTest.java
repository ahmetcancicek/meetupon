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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    private AuthService authService;
    private KeycloakProvider keycloakProvider;
    private LoginResponse loginResponse;
    private RegistrationResponse registrationResponse;

    @BeforeEach
    void setUp() {
        keycloakProvider = Mockito.mock(KeycloakProvider.class);
        authService = new AuthServiceImpl(keycloakProvider);

        registrationResponse = new RegistrationResponse();
        registrationResponse.setId(UUID.randomUUID().toString());
        registrationResponse.setEmail("ericdoor@email.com");
        registrationResponse.setFirstName("Eric");
        registrationResponse.setLastName("Door");
        registrationResponse.setUsername("ericdoor");


        loginResponse = new LoginResponse();
        loginResponse.setAccessToken(UUID.randomUUID().toString());
        loginResponse.setRefreshToken(UUID.randomUUID().toString());
    }

    @Test
    void givenRegistrationRequest_whenRegisterUser_thenReturnRegistrationResponse() {
        // given
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setPassword("HAT79AJA8");
        registrationRequest.setEmail("ericdoor@email.com");
        registrationRequest.setFirstName("Eric");
        registrationRequest.setLastName("Door");
        registrationRequest.setUsername("ericdoor");
        registrationRequest.setRegisterAsAdmin(false);

        given(keycloakProvider.createUser(any(RegistrationRequest.class))).willReturn(registrationResponse);
        given(keycloakProvider.existsByEmail(any(String.class))).willReturn(false);
        given(keycloakProvider.existsByUsername(any(String.class))).willReturn(false);

        // when
        RegistrationResponse createdUser = authService.registerUser(registrationRequest);

        // then
        verify(keycloakProvider, times(1)).createUser(any(RegistrationRequest.class));
        verify(keycloakProvider, times(1)).existsByEmail(any(String.class));
        verify(keycloakProvider, times(1)).existsByUsername(any(String.class));
        assertEquals(registrationResponse, createdUser);
    }

    @Test
    void givenRegistrationRequestWithAlreadyExistsUsername_whenRegisterUser_thenThrowException() {
        // given
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setPassword("HAT79AJA8");
        registrationRequest.setEmail("ericdoor@email.com");
        registrationRequest.setFirstName("Eric");
        registrationRequest.setLastName("Door");
        registrationRequest.setUsername("ericdoor");
        registrationRequest.setRegisterAsAdmin(false);

        given(keycloakProvider.existsByEmail(any(String.class))).willReturn(true);

        // when
        Throwable throwable = catchThrowable(() -> {
            authService.registerUser(registrationRequest);
        });

        // then
        verify(keycloakProvider, times(1)).existsByEmail(any(String.class));
        assertThat(throwable).isInstanceOf(AuthApiBusinessException.class);
    }

    @Test
    void givenRegistrationRequestWithAlreadyExistsEmail_whenRegisterUser_thenThrowException() {
        // given
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setPassword("HAT79AJA8");
        registrationRequest.setEmail("ericdoor@email.com");
        registrationRequest.setFirstName("Eric");
        registrationRequest.setLastName("Door");
        registrationRequest.setUsername("ericdoor");
        registrationRequest.setRegisterAsAdmin(false);

        given(keycloakProvider.existsByEmail(any(String.class))).willReturn(false);
        given(keycloakProvider.existsByUsername(any(String.class))).willReturn(true);

        // when
        Throwable throwable = catchThrowable(() -> {
            authService.registerUser(registrationRequest);
        });

        // then
        verify(keycloakProvider, times(1)).existsByEmail(any(String.class));
        verify(keycloakProvider, times(1)).existsByUsername(any(String.class));
        assertThat(throwable).isInstanceOf(AuthApiBusinessException.class);
    }

    @Test
    void givenLoginRequest_whenAuthenticateUser_thenReturnNewToken() {
        // give
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("HAT79AJA8");
        loginRequest.setUsername("ericdoor");

        given(keycloakProvider.authenticate(any(LoginRequest.class))).willReturn(loginResponse);

        // when
        LoginResponse authenticatedUser = authService.authenticateUser(loginRequest);

        // then
        verify(keycloakProvider, times(1)).authenticate(any(LoginRequest.class));
        assertEquals(loginResponse, authenticatedUser);
    }

    @Test
    void givenEmail_whenIsEmailAlreadyExists_thenReturnTrue() {
        // given
        given(keycloakProvider.existsByEmail(any(String.class))).willReturn(true);

        // when
        boolean status = authService.isEmailAlreadyExists(registrationResponse.getEmail());

        // then
        verify(keycloakProvider, times(1)).existsByEmail(any(String.class));
        assertTrue(status);
    }

    @Test
    void givenUsername_whenIsUsernameAlreadyExists_thenReturnTrue() {
        // given
        given(keycloakProvider.existsByUsername(any(String.class))).willReturn(true);

        // when
        boolean status = authService.isUsernameAlreadyExists(registrationResponse.getUsername());

        // then
        verify(keycloakProvider, times(1)).existsByUsername(any(String.class));
        assertTrue(status);
    }
}