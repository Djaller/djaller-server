package com.djaller.server.auth.config;

import com.djaller.server.auth.service.ClientService;
import com.djaller.server.auth.service.ProviderClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import javax.annotation.PostConstruct;

@Slf4j
@Profile("dev")
@Configuration
@RequiredArgsConstructor
public class InitConfig {

    private static final String AUTH_CLIENT_SECRET = "auth-client-secret";
    private final ProviderClientService providerClientService;
    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    void postConstruct() {
        log.info("Init config: Install defaults");
    }

    @Bean
    public CommandLineRunner createDefaultClients() {
        log.info("Running auth script...");
        return (args) -> {
            log.info("Creating basic auth client...");

            var registeredClient = authRegisteredClient();
            if (clientService.findByClientId(registeredClient.getClientId()) != null)
                clientService.deleteByClientId(registeredClient.getClientId());
            clientService.save(registeredClient);

            var clientRegistration = authClientRegistration();
            if (providerClientService.findByRegistrationId(clientRegistration.getRegistrationId()) != null)
                providerClientService.deleteByRegistrationId(clientRegistration.getRegistrationId());
            providerClientService.save(clientRegistration, true);

            log.info("Auth client created");
        };
    }

    private RegisteredClient authRegisteredClient() {
        return RegisteredClient.withId(OAuthFeignConfig.CLIENT_REGISTRATION_ID)
                .clientId(OAuthFeignConfig.CLIENT_REGISTRATION_ID)
                .clientSecret(passwordEncoder.encode(AUTH_CLIENT_SECRET))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_JWT)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.JWT_BEARER)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .redirectUri("https://oidcdebugger.com/debug")
                .redirectUri("*")
                .redirectUri("**")
                .scope("openid")
                .clientName("Auth Client")
                .build();
    }

    private ClientRegistration authClientRegistration() {
        return ClientRegistration.withRegistrationId(OAuthFeignConfig.CLIENT_REGISTRATION_ID)
                .clientId(OAuthFeignConfig.CLIENT_REGISTRATION_ID)
                .clientSecret(AUTH_CLIENT_SECRET)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .issuerUri("http://localhost:8004")
                .tokenUri("http://localhost:8004/auth/oauth2/token")
                .scope("openid")
                .clientName("Auth Client")
                .build();
    }
}
