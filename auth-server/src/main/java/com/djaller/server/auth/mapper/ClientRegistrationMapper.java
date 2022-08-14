package com.djaller.server.auth.mapper;

import com.djaller.server.auth.domain.ProviderClientEntity;
import com.djaller.server.auth.model.ProviderClientModel;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = MapStringMapper.class)
@Component
public abstract class ClientRegistrationMapper {

    @Autowired
    private MapStringMapper mapStringMapper;

    @Mapping(target = "detailAuthorizationUri", source = "detail.authorizationUri")
    @Mapping(target = "detailTokenUri", source = "detail.tokenUri")
    @Mapping(target = "detailUserInfoUri", source = "detail.userInfoUri")
    @Mapping(target = "detailUserInfoAuthenticationMethod", source = "detail.userInfoAuthenticationMethod")
    @Mapping(target = "detailUserInfoAttributeName", source = "detail.userInfoAttributeName")
    @Mapping(target = "detailJwkSetUri", source = "detail.jwkSetUri")
    @Mapping(target = "detailIssuerUri", source = "detail.issuerUri")
    @Mapping(target = "detailConfigurationMetadata", source = "detail.configurationMetadata")
    public abstract ProviderClientEntity toClientRegistration(ProviderClientModel model);

    @InheritInverseConfiguration
    public abstract ProviderClientModel toClientRegistrationModel(ProviderClientEntity entity);

    public ProviderClientEntity toEntity(ClientRegistration model) {
        var entity = new ProviderClientEntity();
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
        entity.setDetailConfigurationMetadata(mapStringMapper.toMapString(model.getProviderDetails().getConfigurationMetadata()));

        return entity;
    }

    public ClientRegistration toModel(ProviderClientEntity entity) {
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
                .providerConfigurationMetadata(mapStringMapper.toMapObj(entity.getDetailConfigurationMetadata()))
                .build();
    }

    protected AuthenticationMethod map(ProviderClientEntity.AuthenticationMethod method) {
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

    protected ProviderClientEntity.AuthenticationMethod map(AuthenticationMethod method) {
        if (method == null) {
            return null;
        }

        if (AuthenticationMethod.HEADER.equals(method)) {
            return ProviderClientEntity.AuthenticationMethod.HEADER;
        } else if (AuthenticationMethod.FORM.equals(method)) {
            return ProviderClientEntity.AuthenticationMethod.FORM;
        } else if (AuthenticationMethod.QUERY.equals(method)) {
            return ProviderClientEntity.AuthenticationMethod.QUERY;
        }
        throw new IllegalArgumentException("Method [%s] not found".formatted(method));
    }

    protected ClientAuthenticationMethod map(ProviderClientEntity.ClientAuthenticationMethod method) {
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

    protected ProviderClientEntity.ClientAuthenticationMethod map(ClientAuthenticationMethod method) {
        if (method == null) {
            return null;
        }

        if (method == ClientAuthenticationMethod.CLIENT_SECRET_BASIC) {
            return ProviderClientEntity.ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
        } else if (method == ClientAuthenticationMethod.CLIENT_SECRET_POST) {
            return ProviderClientEntity.ClientAuthenticationMethod.CLIENT_SECRET_POST;
        } else if (method == ClientAuthenticationMethod.CLIENT_SECRET_JWT) {
            return ProviderClientEntity.ClientAuthenticationMethod.CLIENT_SECRET_JWT;
        } else if (method == ClientAuthenticationMethod.PRIVATE_KEY_JWT) {
            return ProviderClientEntity.ClientAuthenticationMethod.PRIVATE_KEY_JWT;
        } else if (method == ClientAuthenticationMethod.NONE) {
            return ProviderClientEntity.ClientAuthenticationMethod.NONE;
        } else {
            throw new IllegalArgumentException("Method [%s] not found".formatted(method));
        }
    }

    protected AuthorizationGrantType map(ProviderClientEntity.AuthorizationGrantType grantType) {
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

    protected ProviderClientEntity.AuthorizationGrantType map(AuthorizationGrantType grantType) {
        if (grantType == null) {
            return null;
        }

        if (grantType == AuthorizationGrantType.AUTHORIZATION_CODE) {
            return ProviderClientEntity.AuthorizationGrantType.AUTHORIZATION_CODE;
        } else if (grantType == AuthorizationGrantType.REFRESH_TOKEN) {
            return ProviderClientEntity.AuthorizationGrantType.REFRESH_TOKEN;
        } else if (grantType == AuthorizationGrantType.CLIENT_CREDENTIALS) {
            return ProviderClientEntity.AuthorizationGrantType.CLIENT_CREDENTIALS;
        } else if (grantType == AuthorizationGrantType.PASSWORD) {
            return ProviderClientEntity.AuthorizationGrantType.PASSWORD;
        } else if (grantType == AuthorizationGrantType.JWT_BEARER) {
            return ProviderClientEntity.AuthorizationGrantType.JWT_BEARER;
        } else {
            throw new IllegalArgumentException("Grant Type [%s] not found".formatted(grantType));
        }
    }

    protected Set<String> map(String str) {
        if (str == null) {
            return null;
        }

        return Arrays.stream(str.split(",")).collect(Collectors.toSet());
    }

    protected String map(Set<String> set) {
        if (set == null) {
            return null;
        }
        return String.join(",", set);
    }
}
