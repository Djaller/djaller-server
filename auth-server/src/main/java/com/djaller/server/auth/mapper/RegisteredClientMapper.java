package com.djaller.server.auth.mapper;

import com.djaller.server.auth.domain.AppClientEntity;
import com.djaller.server.auth.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RegisteredClientMapper {

    public RegisteredClient toObject(AppClientEntity client) {
        final var clientAuthenticationMethods = client
                .getClientAuthenticationMethods()
                .stream()
                .map(this::mapTo)
                .collect(Collectors.toUnmodifiableSet());
        final var authorizationGrantTypes = client
                .getAuthorizationGrantTypes()
                .stream()
                .map(this::mapTo)
                .collect(Collectors.toUnmodifiableSet());

        final var redirectUris = client.getRedirectUris();
        final var clientScopes = client.getScopes();

        final var builder = RegisteredClient.withId(client.getId())
                .clientId(client.getClientId())
                .clientIdIssuedAt(client.getClientIdIssuedAt())
                .clientSecret(client.getClientSecret())
                .clientSecretExpiresAt(client.getClientSecretExpiresAt())
                .clientName(client.getClientName())
                .clientAuthenticationMethods(authenticationMethods -> authenticationMethods.addAll(clientAuthenticationMethods))
                .authorizationGrantTypes((grantTypes) -> grantTypes.addAll(authorizationGrantTypes))
                .redirectUris((uris) -> uris.addAll(redirectUris))
                .scopes((scopes) -> scopes.addAll(clientScopes));

        builder.clientSettings(mapTo(client.getClientSettings()));
        builder.tokenSettings(mapTo(client.getTokenSettings()));

        return builder.build();
    }

    public AppClientEntity toEntity(RegisteredClient registeredClient) {
        final var clientAuthenticationMethods = registeredClient
                .getClientAuthenticationMethods()
                .stream()
                .map(this::mapTo)
                .collect(Collectors.toSet());

        final var authorizationGrantTypes = registeredClient
                .getAuthorizationGrantTypes()
                .stream()
                .map(this::mapTo)
                .collect(Collectors.toSet());

        AppClientEntity entity = new AppClientEntity();
        entity.setId(registeredClient.getId());
        entity.setClientId(registeredClient.getClientId());
        entity.setClientIdIssuedAt(registeredClient.getClientIdIssuedAt());
        entity.setClientSecret(registeredClient.getClientSecret());
        entity.setClientSecretExpiresAt(registeredClient.getClientSecretExpiresAt());
        entity.setClientName(registeredClient.getClientName());
        entity.setClientAuthenticationMethods(clientAuthenticationMethods);
        entity.setAuthorizationGrantTypes(authorizationGrantTypes);
        entity.setRedirectUris(registeredClient.getRedirectUris());
        entity.setScopes(registeredClient.getScopes());
        entity.setClientSettings(mapTo(registeredClient.getClientSettings()));
        entity.setTokenSettings(mapTo(registeredClient.getTokenSettings()));

        return entity;
    }

    protected ClientSettings mapTo(AppClientEntity.ClientSettingsEntity clientSettings) {
        if (clientSettings == null) {
            return null;
        }

        ClientSettings.Builder builder = ClientSettings.builder();
        if (clientSettings.getJwkSetUrl() != null) {
            builder = builder.jwkSetUrl(clientSettings.getJwkSetUrl());
        }

        if (clientSettings.getAlgorithm() != null) {
            builder = builder.tokenEndpointAuthenticationSigningAlgorithm(clientSettings.getAlgorithm());
        }

        return builder
                .requireProofKey(clientSettings.isRequireProofKey())
                .requireAuthorizationConsent(clientSettings.isRequireAuthorizationConsent())
                .build();
    }

    protected TokenSettings mapTo(AppClientEntity.TokenSettings tokenSettings) {
        if (tokenSettings == null) {
            return null;
        }

        var builder = TokenSettings.builder();
        if (tokenSettings.getAccessTokenFormat() == null) {
            builder = builder.accessTokenFormat(mapTo(tokenSettings.getAccessTokenFormat()));
        }

        if (tokenSettings.getRefreshTokenTimeToLive() == null) {
            builder = builder.refreshTokenTimeToLive(tokenSettings.getRefreshTokenTimeToLive());
        }

        if (tokenSettings.getIdTokenSignatureAlgorithm() == null) {
            builder = builder.idTokenSignatureAlgorithm(tokenSettings.getIdTokenSignatureAlgorithm());
        }

        return builder
                .accessTokenTimeToLive(tokenSettings.getAccessTokenTimeToLive())
                .reuseRefreshTokens(tokenSettings.isReuseRefreshTokens())
                .build();
    }

    protected AppClientEntity.ClientSettingsEntity mapTo(ClientSettings clientSettings) {
        var settings = new AppClientEntity.ClientSettingsEntity();
        settings.setRequireProofKey(clientSettings.isRequireProofKey());
        settings.setRequireAuthorizationConsent(clientSettings.isRequireAuthorizationConsent());
        settings.setJwkSetUrl(clientSettings.getJwkSetUrl());
        settings.setAlgorithm((SignatureAlgorithm) clientSettings.getTokenEndpointAuthenticationSigningAlgorithm());
        return settings;
    }

    protected AppClientEntity.TokenSettings mapTo(TokenSettings tokenSettings) {
        var settings = new AppClientEntity.TokenSettings();
        settings.setAccessTokenTimeToLive(tokenSettings.getAccessTokenTimeToLive());
        settings.setAccessTokenFormat(mapTo(tokenSettings.getAccessTokenFormat()));
        settings.setReuseRefreshTokens(tokenSettings.isReuseRefreshTokens());
        settings.setRefreshTokenTimeToLive(tokenSettings.getRefreshTokenTimeToLive());
        settings.setIdTokenSignatureAlgorithm(tokenSettings.getIdTokenSignatureAlgorithm());
        return settings;
    }

    protected AppClientEntity.OAuth2TokenFormat mapTo(OAuth2TokenFormat format) {
        if (format == OAuth2TokenFormat.SELF_CONTAINED) {
            return AppClientEntity.OAuth2TokenFormat.SELF_CONTAINED;
        } else if (format == OAuth2TokenFormat.REFERENCE) {
            return AppClientEntity.OAuth2TokenFormat.REFERENCE;
        } else {
            throw new BadRequestException("OAuth2TokenFormat not supported");
        }
    }

    protected OAuth2TokenFormat mapTo(AppClientEntity.OAuth2TokenFormat format) {
        switch (format) {
            case SELF_CONTAINED -> {
                return OAuth2TokenFormat.SELF_CONTAINED;
            }
            case REFERENCE -> {
                return OAuth2TokenFormat.REFERENCE;
            }
            default -> throw new BadRequestException("OAuth2TokenFormat not supported");
        }
    }

    protected AppClientEntity.AuthorizationGrantType mapTo(AuthorizationGrantType grantType) {
        if (grantType == AuthorizationGrantType.REFRESH_TOKEN) {
            return AppClientEntity.AuthorizationGrantType.REFRESH_TOKEN;
        } else if (grantType == AuthorizationGrantType.CLIENT_CREDENTIALS) {
            return AppClientEntity.AuthorizationGrantType.CLIENT_CREDENTIALS;
        } else if (grantType == AuthorizationGrantType.PASSWORD) {
            return AppClientEntity.AuthorizationGrantType.PASSWORD;
        } else if (grantType == AuthorizationGrantType.JWT_BEARER) {
            return AppClientEntity.AuthorizationGrantType.JWT_BEARER;
        } else if (grantType == AuthorizationGrantType.AUTHORIZATION_CODE) {
            return AppClientEntity.AuthorizationGrantType.AUTHORIZATION_CODE;
        } else {
            throw new BadRequestException("AuthorizationGrantType not supported");
        }
    }

    protected AuthorizationGrantType mapTo(AppClientEntity.AuthorizationGrantType grantType) {
        switch (grantType) {

            case REFRESH_TOKEN -> {
                return AuthorizationGrantType.REFRESH_TOKEN;
            }
            case CLIENT_CREDENTIALS -> {
                return AuthorizationGrantType.CLIENT_CREDENTIALS;
            }
            case PASSWORD -> {
                return AuthorizationGrantType.PASSWORD;
            }
            case JWT_BEARER -> {
                return AuthorizationGrantType.JWT_BEARER;
            }
            case AUTHORIZATION_CODE -> {
                return AuthorizationGrantType.AUTHORIZATION_CODE;
            }
            default -> throw new BadRequestException("AuthorizationGrantType not supported");
        }
    }

    protected ClientAuthenticationMethod mapTo(AppClientEntity.ClientAuthenticationMethod grantType) {
        switch (grantType) {
            case CLIENT_SECRET_BASIC -> {
                return ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
            }
            case CLIENT_SECRET_POST -> {
                return ClientAuthenticationMethod.CLIENT_SECRET_POST;
            }
            case CLIENT_SECRET_JWT -> {
                return ClientAuthenticationMethod.CLIENT_SECRET_JWT;
            }
            case PRIVATE_KEY_JWT -> {
                return ClientAuthenticationMethod.PRIVATE_KEY_JWT;
            }
            case NONE -> {
                return ClientAuthenticationMethod.NONE;
            }
            default -> throw new BadRequestException("ClientAuthenticationMethod not supported");
        }
    }

    protected AppClientEntity.ClientAuthenticationMethod mapTo(ClientAuthenticationMethod grantType) {
        if (grantType == ClientAuthenticationMethod.NONE) {
            return AppClientEntity.ClientAuthenticationMethod.NONE;
        } else if (grantType == ClientAuthenticationMethod.CLIENT_SECRET_POST) {
            return AppClientEntity.ClientAuthenticationMethod.CLIENT_SECRET_POST;
        } else if (grantType == ClientAuthenticationMethod.CLIENT_SECRET_BASIC) {
            return AppClientEntity.ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
        } else if (grantType == ClientAuthenticationMethod.CLIENT_SECRET_JWT) {
            return AppClientEntity.ClientAuthenticationMethod.CLIENT_SECRET_JWT;
        } else if (grantType == ClientAuthenticationMethod.PRIVATE_KEY_JWT) {
            return AppClientEntity.ClientAuthenticationMethod.PRIVATE_KEY_JWT;
        } else {
            throw new BadRequestException("ClientAuthenticationMethod not supported");
        }
    }
}
