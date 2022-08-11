package com.microservices.gateway.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
class HomeController {

    @GetMapping
    public String index(Principal principal) {
        return String.format("User Id: [{%s}]", principal.getName());
    }

    @GetMapping("/ping")
    public String ping() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return String.format("Scopes: [{%s}]", authentication.getAuthorities());
    }
}
