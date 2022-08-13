package com.djaller.server.auth.util;

import lombok.SneakyThrows;

import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class KeyGenUtil {

    public static final KeyPairGenerator keygen;
    public static final KeyFactory keyFactory;

    static {
        keygen = generator(4096);
        keyFactory = generateFactory();
    }

    private KeyGenUtil() {
    }

    @SneakyThrows
    public static KeyPairGenerator generator(int keySize) {
        var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize, new SecureRandom());
        return keyPairGenerator;
    }

    @SneakyThrows
    public static KeyFactory generateFactory() {
        return KeyFactory.getInstance("RSA");
    }

}
