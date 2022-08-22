package com.meetupon.auth.security;

import com.meetupon.auth.dto.*;
import com.meetupon.auth.mapper.UserRepresentationMapper;
import com.meetupon.auth.security.JwtTokenProvider;
import com.meetupon.auth.vo.RoleName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakProvider {

    public final JwtTokenProvider jwtTokenProvider;

    /**
     * Create a new user
     *
     * @param registrationRequest
     * @return A user object if successfully created
     */
    public RegistrationResponse createUser(RegistrationRequest registrationRequest) {
        log.info("Trying to save new user to keycloak: [{}]", registrationRequest.toString());
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(registrationRequest.getEmail());
        userRepresentation.setUsername(registrationRequest.getUsername());
        userRepresentation.setFirstName(registrationRequest.getFirstName());
        userRepresentation.setLastName(registrationRequest.getLastName());
        userRepresentation.setCredentials(
                Collections.singletonList(createPasswordCredentials(registrationRequest.getPassword()))
        );
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false);
        Response response = jwtTokenProvider.getUsersResource().create(userRepresentation);
        String userId = CreatedResponseUtil.getCreatedId(response);
        userRepresentation = jwtTokenProvider.getUsersResource().get(userId).toRepresentation();
        log.info("Created new user and saved to keycloak: [{}]", userRepresentation.toString());
        // TODO: Add roles to user for ROLE_ADMIN or ROLE_USER
        return UserRepresentationMapper.INSTANCE.fromUserRepresentation(userRepresentation);
    }

    /**
     * @param loginRequest
     * @return
     */
    public LoginResponse authenticate(LoginRequest loginRequest) {
        log.info("Trying to authenticate user with keycloak: [{}]", loginRequest.getUsername().toString());
        AccessTokenResponse accessTokenResponse = jwtTokenProvider.generateToken(loginRequest.getUsername(), loginRequest.getPassword());
        // TODO:
        return LoginResponse.builder()
                .accessToken(accessTokenResponse.getToken())
                .refreshToken(accessTokenResponse.getRefreshToken())
                .expiresIn(accessTokenResponse.getExpiresIn())
                .refreshExpiresIn(accessTokenResponse.getRefreshExpiresIn())
                .tokenType(accessTokenResponse.getTokenType())
                .build();
    }

    /**
     * Create a password credentials for Keycloak
     *
     * @param password
     * @return A credential if successfully created
     */
    public CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    /**
     * @param userId
     * @param updateUserRequest
     * @return
     */
    public RegistrationResponse updateUser(String userId, UpdateUserRequest updateUserRequest) {
        // TODO: Check if the email already exists in another user
        log.info("Trying to update a user from keycloak: [{}]", updateUserRequest.toString());
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId(userId);
        userRepresentation.setEmail(updateUserRequest.getEmail());
        userRepresentation.setFirstName(updateUserRequest.getFirstName());
        userRepresentation.setLastName(updateUserRequest.getLastName());
        userRepresentation.setCredentials(
                Collections.singletonList(createPasswordCredentials(updateUserRequest.getPassword()))
        );
        jwtTokenProvider.getUsersResource().get(userId).update(userRepresentation);
        log.info("Updated user and saved to keycloak: [{}]", userRepresentation.toString());

        return UserRepresentationMapper.INSTANCE.fromUserRepresentation(userRepresentation);
    }

    /**
     * @param id
     * @param roleName
     */
    public void addToRoleToUser(String id, RoleName roleName) {
        log.info("Trying to get the user with id: [{}]", id.toString());
        UserResource userResource = jwtTokenProvider.getUsersResource().get(id);
        // Get realm role "USER"
        RoleRepresentation userRealmRole = jwtTokenProvider.getKeycloakRealm().roles()
                .get(roleName.toString()).toRepresentation();
        // Assign realm role tester to user
        userResource.roles().realmLevel()
                .add(Arrays.asList(userRealmRole));

        log.info("Added role to user: [{}]", userResource.toRepresentation());
    }

    /**
     * Checks if the user exists with given email
     *
     * @param email
     * @return
     */
    public Boolean existsByEmail(String email) {
        log.info("Trying to find a user from keycloak with email: [{}]", email.toString());
        Integer counter = jwtTokenProvider.getUsersResource().count(email);
        return counter > 0;
    }

    /**
     * Checks if the user exists with given username
     *
     * @param username
     * @return
     */
    public Boolean existsByUsername(String username) {
        log.info("Trying to find a user from keycloak with username: [{}]", username.toString());
        Integer counter = jwtTokenProvider.getUsersResource().count(username);
        return counter > 0;
    }
}
