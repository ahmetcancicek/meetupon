package com.meetupon.auth.security;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtTokenProvider {

    private final String clientSecret;

    private final String clientId;
    private final String serverUrl;
    private final String realm;

    private final Keycloak keycloak;

    public JwtTokenProvider(@Value("${keycloak.credentials.secret}") String clientSecret,
                            @Value("${keycloak.resource}") String clientId,
                            @Value("${keycloak.auth-server-url}") String serverUrl,
                            @Value("${keycloak.realm}") String realm,
                            Keycloak keycloak) {
        this.clientSecret = clientSecret;
        this.clientId = clientId;
        this.serverUrl = serverUrl;
        this.realm = realm;
        this.keycloak = keycloak;
    }

    public AccessTokenResponse generateToken(String username, String password) {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(username)
                .password(password)
                .build().tokenManager().getAccessToken();
    }

    public RealmResource getKeycloakRealm() {
        return keycloak.realm(realm);
    }

    public UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }
}