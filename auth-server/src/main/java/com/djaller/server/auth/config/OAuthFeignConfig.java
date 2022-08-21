package com.djaller.server.auth.config;

import com.djaller.server.auth.util.OAuthClientCredentialsFeignManager;
import com.djaller.server.common.model.HeaderConstants;
import com.djaller.server.common.tenant.model.TenantContext;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OAuthFeignConfig {
    public static final String CLIENT_REGISTRATION_ID = "auth-client-registration";

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Bean
    public RequestInterceptor requestInterceptor() {
        var clientCredentialsFeignManager = new OAuthClientCredentialsFeignManager(authorizedClientManager(), CLIENT_REGISTRATION_ID);

        return requestTemplate -> {
            requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + clientCredentialsFeignManager.getAccessToken());
        };
    }

    @Bean
    public RequestInterceptor tenantInterceptor() {
        return requestTemplate -> {
            String tenantName = TenantContext.getTenantName();
            if (ObjectUtils.isNotEmpty(tenantName)) {
                requestTemplate.header(HeaderConstants.TONTINE_ID, tenantName);
            }
        };
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager() {
        var authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build();

        var authorizedClientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, oAuth2AuthorizedClientService);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        return authorizedClientManager;
    }
}
