package com.djaller.server.auth.mapper;

import com.djaller.server.auth.domain.AppClientEntity;
import com.djaller.server.auth.domain.AuthorizationEntity;
import com.djaller.server.auth.exception.BadRequestException;
import com.djaller.server.auth.mapper.impl.JacksonMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Slf4j
@Component
@AllArgsConstructor
public class OAuth2AuthorizationMapper {
    private final JacksonMapper mapStringMapper;
    private final RegisteredClientMapper registeredClientMapper;

    protected AuthorizationEntity.AuthorizationGrantType map(AuthorizationGrantType authorizationGrantType) {
        if (authorizationGrantType == AuthorizationGrantType.AUTHORIZATION_CODE) {
            return AuthorizationEntity.AuthorizationGrantType.AUTHORIZATION_CODE;
        } else if (authorizationGrantType == AuthorizationGrantType.PASSWORD) {
            return AuthorizationEntity.AuthorizationGrantType.PASSWORD;
        } else if (authorizationGrantType == AuthorizationGrantType.REFRESH_TOKEN) {
            return AuthorizationEntity.AuthorizationGrantType.REFRESH_TOKEN;
        } else if (authorizationGrantType == AuthorizationGrantType.CLIENT_CREDENTIALS) {
            return AuthorizationEntity.AuthorizationGrantType.CLIENT_CREDENTIALS;
        } else {
            throw new BadRequestException("Not supported authorizationGrantType %s".formatted(authorizationGrantType));
        }
    }

    protected AuthorizationGrantType map(AuthorizationEntity.AuthorizationGrantType authorizationGrantType) {
        switch (authorizationGrantType) {

            case AUTHORIZATION_CODE -> {
                return AuthorizationGrantType.AUTHORIZATION_CODE;
            }
            case PASSWORD -> {
                return AuthorizationGrantType.PASSWORD;
            }
            case REFRESH_TOKEN -> {
                return AuthorizationGrantType.REFRESH_TOKEN;
            }
            case CLIENT_CREDENTIALS -> {
                return AuthorizationGrantType.CLIENT_CREDENTIALS;
            }
            default ->
                    throw new BadRequestException("Not supported authorizationGrantType %s".formatted(authorizationGrantType));
        }
    }

    protected OAuth2AuthorizationCode map(AuthorizationEntity.AuthorizationCodeEntity authorizationCode) {
        if (authorizationCode == null || authorizationCode.getValue() == null) {
            return null;
        }

        return new OAuth2AuthorizationCode(
                authorizationCode.getValue(),
                authorizationCode.getIssuedAt(),
                authorizationCode.getExpiresAt());
    }

    protected OAuth2AccessToken map(AuthorizationEntity.AuthorizationAccessTokenEntity accessToken) {
        if (accessToken == null || accessToken.getValue() == null) {
            return null;
        }

        return new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                accessToken.getValue(),
                accessToken.getIssuedAt(),
                accessToken.getExpiresAt(),
                accessToken.getScopes());
    }

    protected OAuth2RefreshToken map(AuthorizationEntity.AuthorizationRefreshTokenEntity refreshToken) {
        if (refreshToken == null || refreshToken.getValue() == null) {
            return null;
        }

        return new OAuth2RefreshToken(
                refreshToken.getValue(),
                refreshToken.getIssuedAt(),
                refreshToken.getExpiresAt()
        );
    }

    protected OidcIdToken map(AuthorizationEntity.AuthorizationOidcIdTokenEntity oidcIdTokenEntity) {
        if (oidcIdTokenEntity == null || oidcIdTokenEntity.getValue() == null) {
            return null;
        }

        return new OidcIdToken(
                oidcIdTokenEntity.getValue(),
                oidcIdTokenEntity.getIssuedAt(),
                oidcIdTokenEntity.getExpiresAt(),
                mapStringMapper.toMapObj(oidcIdTokenEntity.getClaims())
        );
    }

    public OAuth2Authorization toObject(AuthorizationEntity entity) {
        var registeredClient = registeredClientMapper.toObject(entity.getRegisteredClient());
        var builder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .id(entity.getId())
                .principalName(entity.getPrincipalName())
                .authorizationGrantType(map(entity.getAuthorizationGrantType()))
                .attributes(attributes -> attributes.putAll(mapStringMapper.toMapObj(entity.getAttributes())));

        if (entity.getState() != null) {
            builder.attribute(OAuth2ParameterNames.STATE, entity.getState());
        }

        var codeEntity = entity.getAuthorizationCode();
        if (codeEntity != null && codeEntity.getValue() != null) {
            var oAuth2AuthorizationCode = map(codeEntity);
            final var entityMetadata = mapStringMapper.toMapObj(codeEntity.getMetadata());
            builder.token(oAuth2AuthorizationCode, metadata -> metadata.putAll(entityMetadata));
        }

        var accessTokenEntity = entity.getAccessToken();
        if (accessTokenEntity != null && accessTokenEntity.getValue() != null) {
            var accessToken = map(accessTokenEntity);
            final var entityMetadata = mapStringMapper.toMapObj(accessTokenEntity.getMetadata());
            builder.token(accessToken, metadata -> metadata.putAll(entityMetadata));
        }

        var refreshTokenEntity = entity.getRefreshToken();
        if (refreshTokenEntity != null && refreshTokenEntity.getValue() != null) {
            var refreshToken = map(refreshTokenEntity);
            final var entityMetadata = mapStringMapper.toMapObj(refreshTokenEntity.getMetadata());
            builder.token(refreshToken, metadata -> metadata.putAll(entityMetadata));
        }

        var oidcIdTokenEntity = entity.getOidcIdToken();
        if (oidcIdTokenEntity != null && oidcIdTokenEntity.getValue() != null) {
            var idToken = map(oidcIdTokenEntity);
            final var entityMetadata = mapStringMapper.toMapObj(oidcIdTokenEntity.getMetadata());
            builder.token(idToken, metadata -> metadata.putAll(entityMetadata));
        }

        return builder.build();
    }

    public AuthorizationEntity toEntity(OAuth2Authorization authorization) {
        if (authorization == null) {
            return null;
        }

        var registeredClient = new AppClientEntity();
        registeredClient.setId(authorization.getRegisteredClientId());

        var entity = new AuthorizationEntity();
        entity.setId(authorization.getId());
        entity.setRegisteredClient(registeredClient);
        entity.setPrincipalName(authorization.getPrincipalName());
        entity.setAuthorizationGrantType(map(authorization.getAuthorizationGrantType()));
        entity.setAttributes(mapStringMapper.toMapString(authorization.getAttributes()));
        entity.setState(authorization.getAttribute(OAuth2ParameterNames.STATE));

        var authorizationCode = authorization.getToken(OAuth2AuthorizationCode.class);
        if (authorizationCode != null) {
            if (entity.getAuthorizationCode() == null)
                entity.setAuthorizationCode(new AuthorizationEntity.AuthorizationCodeEntity());
            setTokenValues(authorizationCode, entity.getAuthorizationCode());
        }

        var accessToken = authorization.getToken(OAuth2AccessToken.class);
        if (accessToken != null) {
            if (entity.getAccessToken() == null)
                entity.setAccessToken(new AuthorizationEntity.AuthorizationAccessTokenEntity());
            setTokenValues(accessToken, entity.getAccessToken());
            if (accessToken.getToken().getScopes() != null) {
                entity.getAccessToken().setScopes(accessToken.getToken().getScopes());
            }
        }

        var refreshToken = authorization.getToken(OAuth2RefreshToken.class);
        if (refreshToken != null) {
            if (entity.getRefreshToken() == null)
                entity.setRefreshToken(new AuthorizationEntity.AuthorizationRefreshTokenEntity());
            setTokenValues(refreshToken, entity.getRefreshToken());
        }

        var oidcIdToken = authorization.getToken(OidcIdToken.class);
        if (oidcIdToken != null) {
            if (entity.getOidcIdToken() == null)
                entity.setOidcIdToken(new AuthorizationEntity.AuthorizationOidcIdTokenEntity());
            setTokenValues(oidcIdToken, entity.getOidcIdToken());
            entity.getOidcIdToken().setClaims(mapStringMapper.toMapString(oidcIdToken.getClaims()));
        }

        return entity;
    }

    private void setTokenValues(
            @NotNull OAuth2Authorization.Token<?> token,
            @NotNull AuthorizationEntity.AbstractAuthorizationTokenEntity tokenEntity) {
        var oAuth2Token = token.getToken();
        tokenEntity.setValue(oAuth2Token.getTokenValue());
        tokenEntity.setIssuedAt(oAuth2Token.getIssuedAt());
        tokenEntity.setExpiresAt(oAuth2Token.getExpiresAt());
        tokenEntity.setMetadata(mapStringMapper.toMapString(token.getMetadata()));
    }
}
