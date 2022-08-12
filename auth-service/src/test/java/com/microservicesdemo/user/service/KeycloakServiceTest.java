package com.microservicesdemo.user.service;

import com.microservicesdemo.auth.service.KeycloakService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

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