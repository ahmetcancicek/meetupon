package com.microservicesdemo.auth.service;

import com.microservicesdemo.auth.dto.RegistrationRequest;
import com.microservicesdemo.auth.dto.RegistrationResponse;
import com.microservicesdemo.auth.dto.UpdateUserRequest;
import com.microservicesdemo.auth.dto.RoleName;
import com.microservicesdemo.auth.mapper.UserRepresentationMapper;
import com.microservicesdemo.auth.exception.AuthServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final Keycloak keycloak;

    @Value("${keycloak.credentials.secret}")
    String secretKey;

    @Value("${keycloak.resource}")
    String clientId;

    @Value("${keycloak.auth-server-url}")
    String authUrl;

    @Value("${keycloak.realm}")
    public String realm;

    public Optional<UserRepresentation> createUser(RegistrationRequest registrationRequest) {
        log.info("Trying to save new user to keycloak: [{}]", registrationRequest.toString());
        UserRepresentation user = UserRepresentationMapper.INSTANCE.toUserRepresentation(registrationRequest);

        // Create password
        user.setCredentials(Collections.singletonList(createPasswordCredentials(registrationRequest.getPassword())));
        user.setEnabled(true);
        
        // Create user
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        Response response = usersResource.create(user);

        // Get id
        String userId = CreatedResponseUtil.getCreatedId(response);

        // Add role
        if (!registrationRequest.getRegisterAsAdmin())
            addToRoleToUser(userId, RoleName.ROLE_USER.name);
        else
            addToRoleToUser(userId, RoleName.ROLE_ADMIN.name);

        //Get user
        user = usersResource.get(userId).toRepresentation();
        log.info("Created new address and saved to keycloak: [{}]", user.toString());
        return Optional.ofNullable(user);
    }


    public Optional<UserRepresentation> getUser(String username) {
        log.info("Trying to get the user with username: [{}]", username.toString());
        UsersResource usersResource = keycloak.realm(realm).users();
        if (usersResource.search(username, true).isEmpty())
            throw new AuthServiceException("The user could not found");
        UserRepresentation userRepresentation = usersResource.search(username, true).get(0);
        return Optional.ofNullable(userRepresentation);
    }

    public Optional<RegistrationResponse> updateUser(String userId, UpdateUserRequest updateUserRequest) {
        log.info("Trying to save update from keycloak: [{}]", updateUserRequest.toString());
        UserRepresentation user = UserRepresentationMapper.INSTANCE.toUserRepresentationFromUserUpdateRequest(updateUserRequest);
        user.setCredentials(Collections.singletonList(createPasswordCredentials(updateUserRequest.getPassword())));
        UsersResource usersResource = keycloak.realm(realm).users();
        usersResource.get(userId).update(user);
        log.info("Updated new address and saved to keycloak: [{}]", user.toString());
        return Optional.ofNullable(UserRepresentationMapper.INSTANCE.fromUserRepresentation(usersResource.get(userId).toRepresentation()));
    }

    public void addToRoleToUser(String id, String roleName) {
        log.info("Trying to get the user with id: [{}]", id.toString());
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        // Get user
        UserResource userResource = usersResource.get(id);

        // Get realm role "USER"
        RoleRepresentation userRealmRole = realmResource.roles()
                .get(roleName).toRepresentation();

        // Assign realm role tester to user
        userResource.roles().realmLevel() //
                .add(Arrays.asList(userRealmRole));

        log.info("Added role to user: [{}]", userResource.toRepresentation());
    }

    public Optional<AccessTokenResponse> generateToken(String username, String password) {
        // TODO: Refactor to this method to generate token
        return Optional.ofNullable(KeycloakBuilder.builder()
                .serverUrl(authUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .clientSecret(secretKey)
                .username(username)
                .password(password)
                .build().tokenManager().getAccessToken());
    }

    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}
