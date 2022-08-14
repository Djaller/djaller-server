package com.djaller.server.auth.domain;

import lombok.*;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_clients")
public class AppClientEntity {

    @Id
    private String id;

    @Column(unique = true)
    private String clientId;
    private Instant clientIdIssuedAt;
    private String clientSecret;
    private Instant clientSecretExpiresAt;
    private String clientName;

    @Enumerated(EnumType.STRING)
    @CollectionTable(
            indexes = @Index(name = "idx_client_id_app_client_client_authentication_methods", columnList = "client_id"),
            joinColumns = @JoinColumn(name = "client_id"),
            name = "app_client_client_authentication_methods"
    )
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;

    @Enumerated(EnumType.STRING)
    @CollectionTable(
            indexes = @Index(name = "idx_client_id_app_client_authorization_grant_types", columnList = "client_id"),
            joinColumns = @JoinColumn(name = "client_id"),
            name = "app_client_authorization_grant_types"
    )
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<AuthorizationGrantType> authorizationGrantTypes;

    @CollectionTable(
            indexes = @Index(name = "idx_client_id_app_client_redirect_uris", columnList = "client_id"),
            joinColumns = @JoinColumn(name = "client_id"),
            name = "app_client_redirect_uris"
    )
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> redirectUris;


    @CollectionTable(
            indexes = @Index(name = "idx_client_id_app_client_scopes", columnList = "client_id"),
            joinColumns = @JoinColumn(name = "client_id"),
            name = "app_client_scopes"
    )
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> scopes;

    @OneToOne(cascade = CascadeType.ALL)
    private ClientSettings clientSettings;

    @OneToOne(cascade = CascadeType.ALL)
    private TokenSettings tokenSettings;


    public enum AuthorizationGrantType {
        REFRESH_TOKEN, CLIENT_CREDENTIALS, PASSWORD, JWT_BEARER, AUTHORIZATION_CODE
    }

    public enum ClientAuthenticationMethod {
        CLIENT_SECRET_BASIC, CLIENT_SECRET_POST, CLIENT_SECRET_JWT, PRIVATE_KEY_JWT, NONE,
    }

    public enum OAuth2TokenFormat {
        SELF_CONTAINED, REFERENCE
    }

    @Entity
    @Table(name = "app_client_token_settings")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenSettings {
        @Id
        @GeneratedValue
        @Column(columnDefinition = "uuid")
        private UUID id;
        private Duration accessTokenTimeToLive;

        @Enumerated(EnumType.STRING)
        private OAuth2TokenFormat accessTokenFormat;
        private boolean reuseRefreshTokens = false;
        private Duration refreshTokenTimeToLive;

        @Enumerated(EnumType.STRING)
        private SignatureAlgorithm idTokenSignatureAlgorithm;
    }

    @Entity
    @Table(name = "app_client_client_settings")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClientSettings implements Serializable {
        @Id
        @GeneratedValue
        @Column(columnDefinition = "uuid")
        private UUID id;

        private boolean requireProofKey = false;
        private boolean requireAuthorizationConsent = false;
        private String jwkSetUrl;
        private SignatureAlgorithm algorithm;
    }
}
