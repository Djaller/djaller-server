package com.djaller.server.auth.config;

import com.djaller.server.auth.helper.UserService;
import com.djaller.server.auth.model.CurrentUser;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import java.util.stream.Collectors;

@Slf4j
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String AUTHORITIES_CLAIM = "authorities";

    private final UserService userService;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        var authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer<HttpSecurity>();
        var endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        http
                .requestMatcher(endpointsMatcher)
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .exceptionHandling(exceptions ->
                        exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/auth/sign-in"))
                )
                .apply(authorizationServerConfigurer);

        authorizationServerConfigurer
                .authorizationEndpoint(authorizationEndpoint ->
                        authorizationEndpoint
                                .consentPage("/auth/consent")
                );
        return http.build();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(
                                        "/",
                                        "/swagger-ui.html/**",
                                        "/swagger-ui/**",
                                        "/actuator/**",
                                        "/**/*.ico",
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
                .rememberMe((rememberMe) -> rememberMe
                        .rememberMeServices(rememberMeServices())
                )
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

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            if (context.getTokenType().getValue().equals(OidcParameterNames.ID_TOKEN) || context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
                var principal = context.getPrincipal();

                var authorities = principal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet());
                context.getClaims().claim(AUTHORITIES_CLAIM, authorities);

                if (principal instanceof UsernamePasswordAuthenticationToken token && token.getPrincipal() instanceof CurrentUser user) {
                    context.getClaims().claim("username", user.getUsername());
                    context.getClaims().claims(stringObjectMap -> stringObjectMap.putAll(user.getAttributes()));
                }
            }
        };
    }

    @Bean
    public SpringSessionRememberMeServices rememberMeServices() {
        var rememberMeServices = new SpringSessionRememberMeServices();

        // optionally customize
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }
}
