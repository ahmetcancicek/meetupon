package com.meetupon.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetupon.auth.dto.LoginRequest;
import com.meetupon.auth.dto.LoginResponse;
import com.meetupon.auth.dto.RegistrationRequest;
import com.meetupon.auth.dto.RegistrationResponse;
import com.meetupon.auth.exception.AuthApiBusinessException;
import com.meetupon.auth.service.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthServiceImpl authService;

    @MockBean
    private JwtDecoder jwtDecoder;

    private RegistrationRequest registrationRequest;
    private RegistrationResponse registrationResponse;
    private LoginResponse loginResponse;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        registrationResponse = RegistrationResponse.builder()
                .id(UUID.randomUUID().toString())
                .email("billhouse@mail.com")
                .username("billhouse")
                .firstName("Bill")
                .lastName("House")
                .build();

        registrationRequest = RegistrationRequest.builder()
                .email("billhouse@mail.com")
                .username("billhouse")
                .firstName("Bill")
                .lastName("House")
                .password("H80F7GWU")
                .registerAsAdmin(false)
                .build();

        loginResponse = LoginResponse.builder()
                .accessToken(UUID.randomUUID().toString())
                .refreshToken(UUID.randomUUID().toString())
                .build();

        loginRequest = LoginRequest.builder()
                .username("billhouse")
                .password("H80F7GWU")
                .build();
    }

    @Test
    public void givenUser_whenRegisterUser_thenReturnUser() throws Exception {
        // given
        given(authService.registerUser(any())).willReturn(registrationResponse);

        // when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(registrationRequest));

        // then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(registrationResponse.getId()))
                .andExpect(jsonPath("$.data.username").value(registrationResponse.getUsername()))
                .andExpect(jsonPath("$.data.firstName").value(registrationResponse.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(registrationResponse.getLastName()))
                .andExpect(jsonPath("$.data.email").value(registrationResponse.getEmail()));
    }

    @Test
    public void givenExistingUser_whenLoginUser_thenReturnToken() throws Exception {
        // given
        given(authService.authenticateUser(any())).willReturn(loginResponse);

        // when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(loginRequest));

        // then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").value(loginResponse.getAccessToken()))
                .andExpect(jsonPath("$.data.refreshToken").value(loginResponse.getRefreshToken()));
    }

    @Test
    public void givenExistingUserWithEmail_whenRegisterUser_thenThrowException() throws Exception {
        // given
        given(authService.registerUser(any())).willThrow(new AuthApiBusinessException("auth-service.user.existsEmail"));

        // when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(registrationRequest));

        // then
        mockMvc.perform(mockRequest)
                .andExpect(status().isExpectationFailed());
    }

    @Test
    public void givenExistingUserWithUsername_whenRegisterUser_thenThrowException() throws Exception {
        // given
        given(authService.registerUser(any())).willThrow(new AuthApiBusinessException("auth-service.user.existsUsername"));

        // when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(registrationRequest));

        // then
        mockMvc.perform(mockRequest)
                .andExpect(status().isExpectationFailed());
    }
}