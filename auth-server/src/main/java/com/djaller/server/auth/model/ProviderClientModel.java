package com.djaller.server.auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Schema(name = "ProviderClient")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProviderClientModel {
    private UUID id;

    private String registrationId;
    private String clientId;
    private String clientName;
    private String clientSecret;
    private ClientAuthenticationMethod clientAuthenticationMethod;
    private AuthorizationGrantType authorizationGrantType;
    private Set<String> redirectUri;
    private Set<String> scopes;
    private DetailClientRegistration detail;
    private Boolean systemClient;

    public enum AuthenticationMethod {
        HEADER, FORM, QUERY
    }

    public enum AuthorizationGrantType {
        AUTHORIZATION_CODE, REFRESH_TOKEN, CLIENT_CREDENTIALS, PASSWORD, JWT_BEARER,
    }

    public enum ClientAuthenticationMethod {
        CLIENT_SECRET_BASIC, CLIENT_SECRET_POST, CLIENT_SECRET_JWT, PRIVATE_KEY_JWT, NONE
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailClientRegistration {
        private String authorizationUri;
        private String tokenUri;
        private String userInfoUri;
        private String userInfoAttributeName;
        private AuthenticationMethod userInfoAuthenticationMethod;
        private String jwkSetUri;
        private String issuerUri;
        private Map<String, Object> configurationMetadata;
    }
}
