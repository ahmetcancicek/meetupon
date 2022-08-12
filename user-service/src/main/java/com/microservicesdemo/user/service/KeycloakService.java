package com.microservicesdemo.user.service;

import com.microservicesdemo.user.dto.RoleName;
import com.microservicesdemo.user.dto.UserUpdateRequest;
import com.microservicesdemo.user.exception.UserServiceException;
import com.microservicesdemo.user.mapper.UserMapper;
import com.microservicesdemo.user.dto.UserRequest;
import com.microservicesdemo.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
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

    @Value("${keycloak.realm}")
    public String realm;

    public Optional<UserResponse> createUser(UserRequest userRequest) {
        log.info("Trying to save new user to keycloak: [{}]", userRequest.toString());
        UserRepresentation user = UserMapper.INSTANCE.toUser(userRequest);
        user.setCredentials(Collections.singletonList(createPasswordCredentials(userRequest.getPassword())));
        //
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        Response response = usersResource.create(user);
        //
        String userId = CreatedResponseUtil.getCreatedId(response);
        //
        if (!userRequest.getRegisterAsAdmin())
            addToRoleToUser(userId, RoleName.ROLE_USER.name);
        else
            addToRoleToUser(userId, RoleName.ROLE_ADMIN.name);
        //
        UserResponse userResponse = UserMapper.INSTANCE.fromUser(usersResource.get(userId).toRepresentation());
        log.info("Created new address and saved to keycloak: [{}]", user.toString());
        return Optional.ofNullable(userResponse);
    }

    public Optional<UserResponse> getUser(String username) {
        log.info("Trying to get the user with username: [{}]", username.toString());
        UsersResource usersResource = keycloak.realm(realm).users();
        if (usersResource.search(username, true).isEmpty())
            throw new UserServiceException("The user could not found");
        UserRepresentation userRepresentation = usersResource.search(username, true).get(0);
        return Optional.ofNullable(UserMapper.INSTANCE.fromUser(userRepresentation));
    }

    public Optional<UserResponse> updateUser(String userId, UserUpdateRequest userUpdateRequest) {
        log.info("Trying to save update from keycloak: [{}]", userUpdateRequest.toString());
        UserRepresentation user = UserMapper.INSTANCE.toUserFromUserUpdateRequest(userUpdateRequest);
        user.setCredentials(Collections.singletonList(createPasswordCredentials(userUpdateRequest.getPassword())));
        UsersResource usersResource = keycloak.realm(realm).users();
        usersResource.get(userId).update(user);
        log.info("Updated new address and saved to keycloak: [{}]", user.toString());
        return Optional.ofNullable(UserMapper.INSTANCE.fromUser(usersResource.get(userId).toRepresentation()));
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

    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}
