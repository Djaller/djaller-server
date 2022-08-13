package com.djaller.server.auth.service;

import com.djaller.server.auth.domain.AlgoKeyEntity;
import com.djaller.server.auth.repo.AlgoKeyRepo;
import com.djaller.server.auth.util.KeyGenUtil;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeyPairService {

    private final AlgoKeyRepo algoKeyRepo;

    @PostConstruct
    private void init() {
        if (algoKeyRepo.count() == 0) {
            addNewRSAKey();
        }
    }

    public List<JWK> getRSAKeys() {
        return algoKeyRepo
                .findAll()
                .stream()
                .map(this::toRSAKey)
                .collect(Collectors.toList());
    }

    public void addNewRSAKey() {
        var keyPair = KeyGenUtil.keygen.generateKeyPair();
        var entity = new AlgoKeyEntity();
        entity.setPublicKey(keyPair.getPublic());
        entity.setPrivateKey(keyPair.getPrivate());
        entity.setAlgorithm("RSA");

        algoKeyRepo.save(entity);
    }

    private RSAKey toRSAKey(AlgoKeyEntity entity) {
        return new RSAKey.Builder((RSAPublicKey) entity.getPublicKey())
                .privateKey((RSAPrivateKey) entity.getPrivateKey())
                .keyID(entity.getId().toString())
                .build();
    }
}
