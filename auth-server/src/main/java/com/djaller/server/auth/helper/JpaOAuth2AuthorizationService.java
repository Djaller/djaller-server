package com.djaller.server.auth.helper;

import com.djaller.server.auth.mapper.OAuth2AuthorizationMapper;
import com.djaller.server.auth.service.OAuth2_AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@RequiredArgsConstructor
public class JpaOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final OAuth2_AuthorizationService oAuth2AuthorizationService;
    private final OAuth2AuthorizationMapper oAuth2AuthorizationMapper;

    @Override
    public void save(OAuth2Authorization authorization) {
        var entity = oAuth2AuthorizationMapper.toEntity(authorization);
        oAuth2AuthorizationService.save(entity);
    }

    @Override
    public void remove(@NotNull OAuth2Authorization authorization) {
        oAuth2AuthorizationService.removeById(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(@NotNull String id) {
        return oAuth2AuthorizationService
                .findById(id)
                .map(oAuth2AuthorizationMapper::toObject)
                .orElse(null);
    }

    @Override
    public OAuth2Authorization findByToken(@NotNull String token, OAuth2TokenType tokenType) {
        return oAuth2AuthorizationService
                .findByToken(token, tokenType)
                .map(oAuth2AuthorizationMapper::toObject)
                .orElse(null);
    }
}


