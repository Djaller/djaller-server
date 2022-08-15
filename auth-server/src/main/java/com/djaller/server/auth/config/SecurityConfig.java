package com.djaller.server.auth.config;

import com.djaller.server.auth.helper.UserService;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http
                .exceptionHandling(exceptions ->
                        exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/auth/sign-in"))
                );
        return http.build();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(
                                        "/",
                                        "/swagger-ui.html/**",
                                        "/swagger-ui/**",
                                        "/actuator/**",
                                        "/v3/api-docs/**",
                                        "/h2-console/**",
                                        // Auth
                                        "/auth/oauth2/**",
                                        "/auth/sign-in/**",
                                        "/error/**",
                                        "/auth/api/**"
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(loginConfigurer -> loginConfigurer
                        .loginPage("/auth/sign-in")
                        .loginProcessingUrl("/hidden/login")
                        .failureUrl("/auth/sign-in?error=true")
                        .defaultSuccessUrl("/auth/api/redirect")
                )
                .oauth2Login(l -> l
                        .authorizationEndpoint(a -> a.baseUri("/auth/oauth2/authorize"))
                        .redirectionEndpoint(r -> r.baseUri("/auth/oauth2/callback/*"))
                        .userInfoEndpoint(u -> u.userService(userService))
                        .failureUrl("/auth/sign-in?error=oauth2")
                        .loginPage("/auth/sign-in")
                        .loginProcessingUrl("/hidden/login")
                )
                .csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
        //
        ;
        return http.build();
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder()
                .authorizationEndpoint("/auth/oauth2/authorize")
                .tokenEndpoint("/auth/oauth2/token")
                .jwkSetEndpoint("/auth/oauth2/jwks")
                .tokenRevocationEndpoint("/auth/oauth2/revoke")
                .tokenIntrospectionEndpoint("/auth/oauth2/introspect")
                .oidcUserInfoEndpoint("/auth/oauth2/userinfo")
                .oidcClientRegistrationEndpoint("/auth/oauth2/register")
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }
}
