package com.djaller.server.auth.domain;


import com.djaller.server.common.tenant.jpa.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authorizations")
public class AuthorizationEntity extends AbstractBaseEntity {
    @Id
    @Column
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(updatable = false, nullable = false, name = "registered_client_id")
    private AppClientEntity registeredClient;

    private String principalName;

    @Enumerated(EnumType.STRING)
    private AuthorizationGrantType authorizationGrantType;

    @CollectionTable(name = "authorizations_attributes")
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, MapString> attributes;

    @Column(length = 500)
    private String state;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private AuthorizationCodeEntity authorizationCode;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private AuthorizationAccessTokenEntity accessToken;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private AuthorizationRefreshTokenEntity refreshToken;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private AuthorizationOidcIdTokenEntity oidcIdToken;

    public enum AuthorizationGrantType {
        AUTHORIZATION_CODE, PASSWORD, REFRESH_TOKEN, CLIENT_CREDENTIALS,
    }

    @MappedSuperclass
    public static abstract class AbstractAuthorizationTokenEntity extends AbstractBaseEntity {
        public abstract void setValue(String value);

        public abstract void setIssuedAt(Instant value);

        public abstract void setExpiresAt(Instant value);

        public abstract void setMetadata(Map<String, MapString> metadata);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "authorization_codes")
    public static class AuthorizationCodeEntity extends AbstractAuthorizationTokenEntity {

        @Id
        @GeneratedValue
        @Column(columnDefinition = "uuid")
        private UUID id;

        @Column(columnDefinition = "text", nullable = false)
        private String value;
        private Instant issuedAt;
        private Instant expiresAt;

        @CollectionTable(name = "authorization_code_metadata")
        @ElementCollection(fetch = FetchType.EAGER)
        private Map<String, MapString> metadata;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "authorization_access_tokens")
    public static class AuthorizationAccessTokenEntity extends AbstractAuthorizationTokenEntity {

        @Id
        @GeneratedValue
        @Column(columnDefinition = "uuid")
        private UUID id;

        @Column(columnDefinition = "text", nullable = false)
        private String value;
        private Instant issuedAt;
        private Instant expiresAt;

        @CollectionTable(name = "authorization_access_token_metadata")
        @ElementCollection(fetch = FetchType.EAGER)
        private Map<String, MapString> metadata;

        @ElementCollection
        @CollectionTable(name = "authorization_access_token_scopes")
        private Set<String> scopes;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "authorization_refresh_tokens")
    public static class AuthorizationRefreshTokenEntity extends AbstractAuthorizationTokenEntity {

        @Id
        @GeneratedValue
        @Column(columnDefinition = "uuid")
        private UUID id;

        @Column(columnDefinition = "text", nullable = false)
        private String value;
        private Instant issuedAt;
        private Instant expiresAt;

        @CollectionTable(name = "authorization_refresh_token_metadata")
        @ElementCollection(fetch = FetchType.EAGER)
        private Map<String, MapString> metadata;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "authorization_oidc_id_tokens")
    public static class AuthorizationOidcIdTokenEntity extends AbstractAuthorizationTokenEntity {

        @Id
        @GeneratedValue
        @Column(columnDefinition = "uuid")
        private UUID id;

        @Column(columnDefinition = "text", nullable = false)
        private String value;
        private Instant issuedAt;
        private Instant expiresAt;

        @CollectionTable(name = "authorization_oidc_id_token_metadata")
        @ElementCollection(fetch = FetchType.EAGER)
        private Map<String, MapString> metadata;

        @CollectionTable(name = "authorization_oidc_id_token_claims")
        @ElementCollection(fetch = FetchType.EAGER)
        private Map<String, MapString> claims;
    }
}
