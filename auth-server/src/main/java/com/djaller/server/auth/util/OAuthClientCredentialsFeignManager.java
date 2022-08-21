package com.djaller.server.auth.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Slf4j
public class OAuthClientCredentialsFeignManager {

    private final OAuth2AuthorizedClientManager manager;
    private final Authentication principal;
    private final String clientId;

    public OAuthClientCredentialsFeignManager(OAuth2AuthorizedClientManager manager, String clientId) {
        this.manager = manager;
        this.clientId = clientId;
        this.principal = createPrincipal();
    }

    private Authentication createPrincipal() {
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.emptySet();
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return this;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            }

            @Override
            public String getName() {
                return clientId;
            }
        };
    }

    public String getAccessToken() {
        try {
            var oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
                    .withClientRegistrationId(clientId)
                    .principal(principal)
                    .build();

            var client = manager.authorize(oAuth2AuthorizeRequest);
            if (Objects.isNull(client)) {
                throw new IllegalStateException("client credentials flow on " + clientId + " failed, client is null");
            }

            return client.getAccessToken().getTokenValue();
        } catch (Exception exp) {
            log.error("client credentials error " + exp.getMessage(), exp);
            return null;
        }
    }
}
