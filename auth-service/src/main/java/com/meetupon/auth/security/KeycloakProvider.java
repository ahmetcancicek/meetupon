package com.meetupon.auth.security;

import com.meetupon.auth.dto.*;
import com.meetupon.auth.mapper.UserRepresentationMapper;
import com.meetupon.auth.security.JwtTokenProvider;
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
import java.util.Optional;

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
    public Optional<UserRepresentation> createUser(RegistrationRequest registrationRequest) {
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
        return Optional.ofNullable(userRepresentation);
    }

    /**
     * @param loginRequest
     * @return
     */
    public Optional<AccessTokenResponse> authenticate(LoginRequest loginRequest) {
        log.info("Trying to authenticate user with keycloak: [{}]", loginRequest.getUsername().toString());
        return jwtTokenProvider.generateToken(loginRequest.getUsername(), loginRequest.getPassword());
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
    public Optional<RegistrationResponse> updateUser(String userId, UpdateUserRequest updateUserRequest) {
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

        return Optional.ofNullable(
                UserRepresentationMapper.INSTANCE.fromUserRepresentation(userRepresentation));
    }

    /**
     * @param id
     * @param roleName
     */
    public void addToRoleToUser(String id, String roleName) {
        log.info("Trying to get the user with id: [{}]", id.toString());
        UserResource userResource = jwtTokenProvider.getUsersResource().get(id);
        // Get realm role "USER"
        RoleRepresentation userRealmRole = jwtTokenProvider.getKeycloakRealm().roles()
                .get(roleName).toRepresentation();
        // Assign realm role tester to user
        userResource.roles().realmLevel()
                .add(Arrays.asList(userRealmRole));
        log.info("Added role to user: [{}]", userResource.toRepresentation());
    }

    /**
     * Checks if the user exists with given email
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
     * @param username
     * @return
     */
    public Boolean existsByUsername(String username) {
        log.info("Trying to find a user from keycloak with username: [{}]", username.toString());
        Integer counter = jwtTokenProvider.getUsersResource().count(username);
        return counter > 0;
    }
}
