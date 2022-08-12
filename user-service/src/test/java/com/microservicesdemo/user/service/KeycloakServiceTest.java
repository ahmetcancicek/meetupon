package com.microservicesdemo.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class KeycloakServiceTest {

    @InjectMocks
    private KeycloakService keycloakService;

    @Test
    void whenCreateUser_thenCreateAndReturnUser() {

    }

    @Test
    void whenGetUser_thenReturnUser() {

    }

    @Test
    void whenUpdateUser_thenUpdateAndReturnUser() {

    }
}