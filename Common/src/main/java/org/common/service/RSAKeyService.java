package org.common.service;

import lombok.RequiredArgsConstructor;
import org.common.model.RSAKeyPair;
import org.common.repository.rsa.RSAKeyPairRepository;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.KeyPair;

@Service
@RequiredArgsConstructor
public class RSAKeyService {

    private final RSAKeyPairRepository rsaKeyPairRepository;
    private final CryptoKeyService cryptoKeyService;



    public Key getPrivateKey() {
        return rsaKeyPairRepository
                .findFirstByPrivateKeyIsNotNullOrderByCreatedAtDesc()
                .getPrivateKey();
    }



    public Key getPublicKey() {
        return rsaKeyPairRepository
                .findFirstByPublicKeyIsNotNullOrderByCreatedAtDesc()
                .getPublicKey();
    }



    public void createKeyPair() {
        try {
            if (rsaKeyPairRepository.findAll().isEmpty()) {
                KeyPair keyPair = cryptoKeyService.generateKeyPair();
                RSAKeyPair keyPairRSAEntity = RSAKeyPair
                        .builder()
                        .publicKey(keyPair.getPublic())
                        .privateKey(keyPair.getPrivate())
                        .build();
                rsaKeyPairRepository.save(keyPairRSAEntity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
