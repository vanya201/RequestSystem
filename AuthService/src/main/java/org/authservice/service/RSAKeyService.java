package org.authservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.authservice.repositories.KeyPairRepository;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
@RequiredArgsConstructor
public class RSAKeyService {
    private final KeyPairRepository keyPairRepository;

    @Transactional
    public Key getPrivateKey() {
         return keyPairRepository
                .findFirstByPrivateKeyIsNotNullOrderByCreatedAtDesc()
                .getPrivateKey();
    }


    @Transactional
    public Key getPublicKey() {
        return keyPairRepository
                .findFirstByPublicKeyIsNotNullOrderByCreatedAtDesc()
                .getPublicKey();
    }
}
