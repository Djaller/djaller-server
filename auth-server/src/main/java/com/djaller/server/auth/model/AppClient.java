package com.djaller.server.auth.model;

import com.djaller.server.auth.domain.AppClientEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppClient implements Serializable {
    private String id;

    @NotEmpty
    private String clientId;
    private Instant clientIdIssuedAt;

    @JsonIgnore
    private String clientSecret;
    private Instant clientSecretExpiresAt;

    @NotEmpty
    private String clientName;
    private Set<AppClientEntity.ClientAuthenticationMethod> clientAuthenticationMethods;
    private Set<AppClientEntity.AuthorizationGrantType> authorizationGrantTypes;
    private Set<String> redirectUris;
    private Set<String> scopes;

    private ClientSettings clientSettings;
    private TokenSettings tokenSettings;

    public enum OAuth2TokenFormat {
        SELF_CONTAINED, REFERENCE
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenSettings implements Serializable {
        private Duration accessTokenTimeToLive;

        @Builder.Default
        private OAuth2TokenFormat accessTokenFormat = OAuth2TokenFormat.SELF_CONTAINED;
        private boolean reuseRefreshTokens;
        private Duration refreshTokenTimeToLive;

        @Builder.Default
        private SignatureAlgorithm idTokenSignatureAlgorithm = SignatureAlgorithm.RS256;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClientSettings implements Serializable {

        @Builder.Default
        private boolean requireProofKey = false;

        @Builder.Default
        private boolean requireAuthorizationConsent = false;

        private String jwkSetUrl;
        private SignatureAlgorithm algorithm;
    }
}
