package com.microservicesdemo.user.mapper;

import com.microservicesdemo.auth.dto.RegistrationRequest;
import com.microservicesdemo.auth.dto.RegistrationResponse;
import com.microservicesdemo.auth.dto.UpdateUserRequest;
import com.microservicesdemo.auth.mapper.UserRepresentationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserRepresentationMapperTest {

    private UserRepresentationMapper userRepresentationMapper;

    @BeforeEach
    void setUp() {
        userRepresentationMapper = UserRepresentationMapper.INSTANCE;
    }

    @Test
    void whenFromUserRequest_thenReturnUserRepresentation() {
        RegistrationRequest userRequest = RegistrationRequest.builder()
                .email("billhouse@mail.com")
                .username("billhouse")
                .firstName("Bill")
                .lastName("House")
                .registerAsAdmin(false)
                .build();

        UserRepresentation userRepresentation = userRepresentationMapper.toUserRepresentation(userRequest);

        assertEquals(userRequest.getEmail(), userRepresentation.getEmail());
        assertEquals(userRequest.getUsername(), userRepresentation.getUsername());
        assertEquals(userRequest.getFirstName(), userRepresentation.getFirstName());
        assertEquals(userRequest.getLastName(), userRepresentation.getLastName());
    }

    @Test
    void whenFromUserRepresentation_thenReturnUserResponse() {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId(UUID.randomUUID().toString());
        userRepresentation.setEmail("billhouse@mail.com");
        userRepresentation.setUsername("billhouse");
        userRepresentation.setFirstName("Bill");
        userRepresentation.setLastName("House");
        userRepresentation.setEnabled(true);

        RegistrationResponse registrationResponse = userRepresentationMapper.fromUserRepresentation(userRepresentation);

        assertEquals(userRepresentation.getId(), registrationResponse.getId());
        assertEquals(userRepresentation.getEmail(), registrationResponse.getEmail());
        assertEquals(userRepresentation.getUsername(), registrationResponse.getUsername());
        assertEquals(userRepresentation.getFirstName(), registrationResponse.getFirstName());
        assertEquals(userRepresentation.getLastName(), registrationResponse.getLastName());
    }

    @Test
    void whenFromUserUpdateRequest_thenReturnUserRepresentation() {
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .email("billhouse@mail.com")
                .firstName("Bill")
                .lastName("House")
                .enabled(true)
                .registerAsAdmin(false)
                .build();

        UserRepresentation userRepresentation = userRepresentationMapper.toUserRepresentationFromUserUpdateRequest(updateUserRequest);

        assertEquals(updateUserRequest.getEmail(), userRepresentation.getEmail());
        assertEquals(updateUserRequest.getFirstName(), userRepresentation.getFirstName());
        assertEquals(updateUserRequest.getLastName(), userRepresentation.getLastName());
        assertEquals(updateUserRequest.getEnabled(), userRepresentation.isEnabled());
    }
}