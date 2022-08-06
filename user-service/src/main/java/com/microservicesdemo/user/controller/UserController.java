package com.microservicesdemo.user.controller;

import com.microservicesdemo.user.model.User;
import com.microservicesdemo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    // TODO: Refactors to serve response and request operation with DTO

    private final UserService userService;

    @PostMapping
    public User saveUser(@Valid @RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping
    public User getUser(String id) {
        return userService.findById(id)
                .orElseThrow(() -> new NoSuchElementException(""));
    }
}
