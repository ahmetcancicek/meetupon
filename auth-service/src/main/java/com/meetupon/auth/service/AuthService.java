package com.meetupon.auth.service;

import com.meetupon.auth.dto.LoginRequest;
import com.meetupon.auth.dto.LoginResponse;
import com.meetupon.auth.dto.RegistrationRequest;
import com.meetupon.auth.dto.RegistrationResponse;

public interface AuthService {

    RegistrationResponse registerUser(RegistrationRequest registrationRequest);

    LoginResponse authenticateUser(LoginRequest loginRequest);

    Boolean isEmailAlreadyExists(String email);

    Boolean isUsernameAlreadyExists(String username);

}
