package com.djaller.server.auth.mapper;

import com.djaller.server.auth.domain.ClientRegistrationEntity;
import com.djaller.server.auth.domain.MapString;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class ClientRegistrationMapper {
    private final MapStringMapper mapStringMapper;

    public ClientRegistrationEntity toEntity(ClientRegistration model) {
        var entity = new ClientRegistrationEntity();
        entity.setRegistrationId(model.getRegistrationId());
        entity.setClientId(model.getClientId());
        entity.setClientName(model.getClientName());
        entity.setClientSecret(model.getClientSecret());
        entity.setClientAuthenticationMethod(map(model.getClientAuthenticationMethod()));
        entity.setAuthorizationGrantType(map(model.getAuthorizationGrantType()));
        entity.setRedirectUri(model.getRedirectUri());
        entity.setScopes(model.getScopes());

        // Details
        entity.setDetailAuthorizationUri(model.getProviderDetails().getAuthorizationUri());
        entity.setDetailUserInfoUri(model.getProviderDetails().getUserInfoEndpoint().getUri());
        entity.setDetailUserInfoAttributeName(model.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName());
        entity.setDetailUserInfoAuthenticationMethod(map(model.getProviderDetails().getUserInfoEndpoint().getAuthenticationMethod()));
        entity.setDetailJwkSetUri(model.getProviderDetails().getJwkSetUri());
        entity.setDetailTokenUri(model.getProviderDetails().getTokenUri());
        entity.setDetailIssuerUri(model.getProviderDetails().getIssuerUri());
        entity.setDetailConfigurationMetadata(toMapString(model.getProviderDetails().getConfigurationMetadata()));

        return entity;
    }

    public ClientRegistration toModel(ClientRegistrationEntity entity) {
        return ClientRegistration
                .withRegistrationId(entity.getRegistrationId())
                .clientId(entity.getClientId())
                .clientName(entity.getClientName())
                .clientSecret(entity.getClientSecret())
                .clientAuthenticationMethod(map(entity.getClientAuthenticationMethod()))
                .authorizationGrantType(map(entity.getAuthorizationGrantType()))
                .redirectUri(entity.getRedirectUri())
                .scope(entity.getScopes())
                .authorizationUri(entity.getDetailAuthorizationUri())
                .tokenUri(entity.getDetailTokenUri())
                .userInfoUri(entity.getDetailUserInfoUri())
                .userInfoAuthenticationMethod(map(entity.getDetailUserInfoAuthenticationMethod()))
                .userNameAttributeName(entity.getDetailUserInfoAttributeName())
                .jwkSetUri(entity.getDetailJwkSetUri())
                .issuerUri(entity.getDetailIssuerUri())
                .providerConfigurationMetadata(toMapObj(entity.getDetailConfigurationMetadata()))
                .build();
    }

    private AuthenticationMethod map(ClientRegistrationEntity.AuthenticationMethod method) {
        if (method == null) {
            return null;
        }

        switch (method) {
            case HEADER -> {
                return AuthenticationMethod.HEADER;
            }
            case FORM -> {
                return AuthenticationMethod.FORM;
            }
            case QUERY -> {
                return AuthenticationMethod.QUERY;
            }
            default -> {
                throw new IllegalArgumentException("Method [%s] not found".formatted(method));
            }
        }
    }

    private ClientRegistrationEntity.AuthenticationMethod map(AuthenticationMethod method) {
        if (method == null) {
            return null;
        }

        if (AuthenticationMethod.HEADER.equals(method)) {
            return ClientRegistrationEntity.AuthenticationMethod.HEADER;
        } else if (AuthenticationMethod.FORM.equals(method)) {
            return ClientRegistrationEntity.AuthenticationMethod.FORM;
        } else if (AuthenticationMethod.QUERY.equals(method)) {
            return ClientRegistrationEntity.AuthenticationMethod.QUERY;
        }
        throw new IllegalArgumentException("Method [%s] not found".formatted(method));
    }

    private Map<String, Object> toMapObj(Map<String, MapString> maps) {
        if (maps == null) {
            return null;
        }

        var result = new HashMap<String, Object>();
        maps.forEach((key, mapString) -> result.put(key, mapStringMapper.toObject(mapString)));

        return result;
    }

    private Map<String, MapString> toMapString(Map<String, Object> maps) {
        if (maps == null) {
            return null;
        }

        var result = new HashMap<String, MapString>();
        maps.forEach((key, obj) -> result.put(key, mapStringMapper.toMapString(obj)));

        return result;
    }

    private ClientAuthenticationMethod map(ClientRegistrationEntity.ClientAuthenticationMethod method) {
        if (method == null) {
            return null;
        }

        switch (method) {
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
            default -> {
                throw new IllegalArgumentException("Method [%s] not found".formatted(method));
            }
        }
    }

    private ClientRegistrationEntity.ClientAuthenticationMethod map(ClientAuthenticationMethod method) {
        if (method == null) {
            return null;
        }

        if (method == ClientAuthenticationMethod.CLIENT_SECRET_BASIC) {
            return ClientRegistrationEntity.ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
        } else if (method == ClientAuthenticationMethod.CLIENT_SECRET_POST) {
            return ClientRegistrationEntity.ClientAuthenticationMethod.CLIENT_SECRET_POST;
        } else if (method == ClientAuthenticationMethod.CLIENT_SECRET_JWT) {
            return ClientRegistrationEntity.ClientAuthenticationMethod.CLIENT_SECRET_JWT;
        } else if (method == ClientAuthenticationMethod.PRIVATE_KEY_JWT) {
            return ClientRegistrationEntity.ClientAuthenticationMethod.PRIVATE_KEY_JWT;
        } else if (method == ClientAuthenticationMethod.NONE) {
            return ClientRegistrationEntity.ClientAuthenticationMethod.NONE;
        } else {
            throw new IllegalArgumentException("Method [%s] not found".formatted(method));
        }
    }

    private AuthorizationGrantType map(ClientRegistrationEntity.AuthorizationGrantType grantType) {
        if (grantType == null) {
            return null;
        }

        switch (grantType) {
            case AUTHORIZATION_CODE -> {
                return AuthorizationGrantType.AUTHORIZATION_CODE;
            }
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
            default -> {
                throw new IllegalArgumentException("Grant Type [%s] not found".formatted(grantType));
            }
        }
    }

    private ClientRegistrationEntity.AuthorizationGrantType map(AuthorizationGrantType grantType) {
        if (grantType == null) {
            return null;
        }

        if (grantType == AuthorizationGrantType.AUTHORIZATION_CODE) {
            return ClientRegistrationEntity.AuthorizationGrantType.AUTHORIZATION_CODE;
        } else if (grantType == AuthorizationGrantType.REFRESH_TOKEN) {
            return ClientRegistrationEntity.AuthorizationGrantType.REFRESH_TOKEN;
        } else if (grantType == AuthorizationGrantType.CLIENT_CREDENTIALS) {
            return ClientRegistrationEntity.AuthorizationGrantType.CLIENT_CREDENTIALS;
        } else if (grantType == AuthorizationGrantType.PASSWORD) {
            return ClientRegistrationEntity.AuthorizationGrantType.PASSWORD;
        } else if (grantType == AuthorizationGrantType.JWT_BEARER) {
            return ClientRegistrationEntity.AuthorizationGrantType.JWT_BEARER;
        } else {
            throw new IllegalArgumentException("Grant Type [%s] not found".formatted(grantType));
        }
    }

}
