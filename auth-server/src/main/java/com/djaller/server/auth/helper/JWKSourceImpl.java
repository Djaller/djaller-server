package com.djaller.server.auth.helper;

import com.djaller.server.auth.service.KeyPairService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JWKSourceImpl implements JWKSource<SecurityContext> {
    private final KeyPairService keyPairService;

    @Cacheable("jwks")
    @Override
    public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) {
        var jwkSet = new JWKSet(keyPairService.getRSAKeys());
        return jwkSelector.select(jwkSet);
    }

}
