package com.djaller.server.auth.domain;

import com.djaller.server.common.tenant.jpa.AbstractBaseEntity;
import lombok.*;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

import javax.persistence.*;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_clients")
public class AppClientEntity extends AbstractBaseEntity {

    @Id
    private String id;

    @Column(unique = true)
    private String clientId;
    private Instant clientIdIssuedAt;
    private String clientSecret;
    private Instant clientSecretExpiresAt;
    private String clientName;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "registeredClient", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AuthorizationEntity> authorization;

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
    private ClientSettingsEntity clientSettings;

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
    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenSettings extends AbstractBaseEntity {
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
    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClientSettingsEntity extends AbstractBaseEntity {
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
