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
import java.util.Base64;


//TODO: Analyze format of the keys
@Service
@RequiredArgsConstructor
public class JWTKeyService {
    private final KeyPairRepository keyPairRepository;

    @Transactional
    public Key getPrivateKey() {
        String privateKey = keyPairRepository.findByIdAndPrivateKeyIsNotNull(1L).getPrivateKey();
        //TODO: replace find by id with find first by Date created
        return getPrivateKeyFromBase64(privateKey);
    }

    @Transactional
    public Key getPublicKey() {
        String publicKey = keyPairRepository.findByIdAndPublicKeyIsNotNull(1L).getPublicKey();
        return getPublicKeyFromBase64(publicKey);
    }

    public static PrivateKey getPrivateKeyFromBase64(String base64PrivateKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(base64PrivateKey);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            throw new RuntimeException("Error while decoding private key", e);
        }
    }

    public static PublicKey getPublicKeyFromBase64(String base64PublicKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException("Error while decoding public key", e);
        }
    }
}
