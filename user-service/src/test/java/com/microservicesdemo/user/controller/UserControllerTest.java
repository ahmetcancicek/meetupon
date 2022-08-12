package com.microservicesdemo.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicesdemo.user.dto.UserRequest;
import com.microservicesdemo.user.dto.UserResponse;
import com.microservicesdemo.user.service.KeycloakService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;


@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private KeycloakService keycloakService;

    @MockBean
    private JwtDecoder jwtDecoder;

    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userResponse = UserResponse.builder()
                .id(UUID.randomUUID().toString())
                .email("billhouse@mail.com")
                .username("billhouse")
                .firstName("Bill")
                .lastName("House")
                .enabled(true)
                .build();

        userRequest = UserRequest.builder()
                .email("billhouse@mail.com")
                .username("billhouse")
                .firstName("Bill")
                .lastName("House")
                .enabled(true)
                .registerAsAdmin(false)
                .build();
    }


    @Test
    void whenSaveUser_thenCreateAndReturnUser() throws Exception {

    }

    @Test
    void whenGetUser_thenReturnUser() {

    }

    @Test
    void whenUpdateUser_thenUpdateAndReturnUser() {

    }
}