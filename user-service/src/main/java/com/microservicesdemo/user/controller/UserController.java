package com.microservicesdemo.user.controller;

import com.microservicesdemo.user.dto.UserRequest;
import com.microservicesdemo.user.dto.UserUpdateRequest;
import com.microservicesdemo.user.exception.UserServiceException;
import com.microservicesdemo.user.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final KeycloakService keycloakService;

    @PostMapping
    public ResponseEntity saveUser(@Valid @RequestBody UserRequest userRequest) {
        return keycloakService.createUser(userRequest).map(userResponse -> {
                    log.info("User added successfully [{}]", userResponse.toString());
                    return ResponseEntity.ok(userResponse);
                })
                .orElseThrow(() -> new UserServiceException("The user could not created"));
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity getUser(@PathVariable("username") String username) {
        return keycloakService.getUser(username).map(userResponse -> {
            log.info("User got successfully [{}]", userResponse.toString());
            return ResponseEntity.ok(userResponse);
        }).orElseThrow(() -> new UserServiceException("The user could not found"));
    }

    @PutMapping(path = "/update/{userId}")
    public ResponseEntity updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        return keycloakService.updateUser(userId, userUpdateRequest).map(userResponse -> {
                    log.info("User updated successfully [{}]", userResponse.toString());
                    return ResponseEntity.ok(userResponse);
                })
                .orElseThrow(() -> new UserServiceException("User could not updated"));
    }
}
