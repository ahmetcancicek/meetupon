package com.meetupon.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetupon.auth.controller.AuthController;
import com.meetupon.auth.dto.RegistrationRequest;
import com.meetupon.auth.dto.RegistrationResponse;
import com.meetupon.auth.service.KeycloakService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;


@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private KeycloakService keycloakService;

    @MockBean
    private JwtDecoder jwtDecoder;

    private RegistrationRequest userRequest;
    private RegistrationResponse registrationResponse;

    @BeforeEach
    void setUp() {
        registrationResponse = RegistrationResponse.builder()
                .id(UUID.randomUUID().toString())
                .email("billhouse@mail.com")
                .username("billhouse")
                .firstName("Bill")
                .lastName("House")
                .build();

        userRequest = RegistrationRequest.builder()
                .email("billhouse@mail.com")
                .username("billhouse")
                .firstName("Bill")
                .lastName("House")
                .registerAsAdmin(false)
                .build();
    }


    @Test
    void whenRegisterUser_thenCreateAndReturnUser() throws Exception {

    }

    @Test
    void whenGetUser_thenReturnUser() {

    }

    @Test
    void whenUpdateUser_thenUpdateAndReturnUser() {

    }
}