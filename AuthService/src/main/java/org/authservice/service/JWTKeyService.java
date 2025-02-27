package org.authservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.authservice.repositories.KeyPairRepository;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Service
@RequiredArgsConstructor
public class JWTKeyService {
    private final KeyPairRepository keyPairRepository;

    @Transactional
    public Key getPrivateKey() {
        byte[] privateKey = keyPairRepository
                .findFirstByPrivateKeyIsNotNullOrderByCreatedAtDesc()
                .getPrivateKey();
        return getPrivateKeyFromBytes(privateKey);
    }

    @Transactional
    public Key getPublicKey() {
        byte[] publicKey = keyPairRepository
                .findFirstByPublicKeyIsNotNullOrderByCreatedAtDesc()
                .getPublicKey();
        return getPublicKeyFromBytes(publicKey);
    }


    public static PrivateKey getPrivateKeyFromBytes(byte[] keyBytes) {
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            throw new RuntimeException("Error while decoding private key", e);
        }
    }


    public static PublicKey getPublicKeyFromBytes(byte[] keyBytes) {
        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException("Error while decoding public key", e);
        }
    }
}
