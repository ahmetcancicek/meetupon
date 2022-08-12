package com.microservicesdemo.user.mapper;

import com.microservicesdemo.user.dto.UserRequest;
import com.microservicesdemo.user.dto.UserResponse;
import com.microservicesdemo.user.dto.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = UserMapper.INSTANCE;
    }

    @Test
    void whenFromUserRequest_thenReturnUser() {
        UserRequest userRequest = UserRequest.builder()
                .email("billhouse@mail.com")
                .username("billhouse")
                .firstName("Bill")
                .lastName("House")
                .enabled(true)
                .registerAsAdmin(false)
                .build();

        UserRepresentation userRepresentation = userMapper.toUser(userRequest);

        assertEquals(userRequest.getEmail(), userRepresentation.getEmail());
        assertEquals(userRequest.getUsername(), userRepresentation.getUsername());
        assertEquals(userRequest.getFirstName(), userRepresentation.getFirstName());
        assertEquals(userRequest.getLastName(), userRepresentation.getLastName());
        assertEquals(userRequest.getEnabled(), userRepresentation.isEnabled());
    }

    @Test
    void whenFromUser_thenReturnUserResponse() {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId(UUID.randomUUID().toString());
        userRepresentation.setEmail("billhouse@mail.com");
        userRepresentation.setUsername("billhouse");
        userRepresentation.setFirstName("Bill");
        userRepresentation.setLastName("House");
        userRepresentation.setEnabled(true);

        UserResponse userResponse = userMapper.fromUser(userRepresentation);

        assertEquals(userRepresentation.getId(), userResponse.getId());
        assertEquals(userRepresentation.getEmail(), userResponse.getEmail());
        assertEquals(userRepresentation.getUsername(), userResponse.getUsername());
        assertEquals(userRepresentation.getFirstName(), userResponse.getFirstName());
        assertEquals(userRepresentation.getLastName(), userResponse.getLastName());
        assertEquals(userRepresentation.isEnabled(), userResponse.getEnabled());
    }

    @Test
    void whenFromUserUpdateRequest_thenReturnUer() {
        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
                .email("billhouse@mail.com")
                .firstName("Bill")
                .lastName("House")
                .enabled(true)
                .registerAsAdmin(false)
                .build();

        UserRepresentation userRepresentation = userMapper.toUserFromUserUpdateRequest(userUpdateRequest);

        assertEquals(userUpdateRequest.getEmail(), userRepresentation.getEmail());
        assertEquals(userUpdateRequest.getFirstName(), userRepresentation.getFirstName());
        assertEquals(userUpdateRequest.getLastName(), userRepresentation.getLastName());
        assertEquals(userUpdateRequest.getEnabled(), userRepresentation.isEnabled());
    }
}