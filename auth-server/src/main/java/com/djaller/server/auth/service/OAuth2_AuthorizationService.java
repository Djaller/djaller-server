package com.djaller.server.auth.service;

import com.djaller.server.auth.domain.AuthorizationEntity;
import com.djaller.server.auth.repo.AuthorizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2_AuthorizationService {
    private final AuthorizationRepository authorizationRepository;

    public void save(@NotNull AuthorizationEntity authorization) {
        authorizationRepository.save(authorization);
    }

    public void removeById(@NotNull String id) {
        authorizationRepository.deleteById(id);
    }

    public Optional<AuthorizationEntity> findById(@NotNull String id) {
        return authorizationRepository.findById(id);
    }

    public Optional<AuthorizationEntity> findByToken(@NotNull String token, OAuth2TokenType tokenType) {
        if (tokenType == null) {
            return authorizationRepository.findOneByToken(token);
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            return authorizationRepository.findByState(token);
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            return authorizationRepository.findByAuthorizationCodeValue(token);
        } else if (OAuth2ParameterNames.ACCESS_TOKEN.equals(tokenType.getValue())) {
            return authorizationRepository.findByAccessTokenValue(token);
        } else if (OAuth2ParameterNames.REFRESH_TOKEN.equals(tokenType.getValue())) {
            return authorizationRepository.findByRefreshTokenValue(token);
        } else {
            return Optional.empty();
        }
    }
}
