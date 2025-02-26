package org.common.seeder;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.common.repository.KeyPairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;


@Component
@AllArgsConstructor
public class PairKeySeeder {

    private final KeyPairRepository keyPairRepository;

    @PostConstruct
    public void init() throws Exception {
        generateAndSaveKeyPair();
    }

    private void generateAndSaveKeyPair() throws Exception {
        if (keyPairRepository.existsById(1L))
            return;

        KeyPair keyPair = generateKeyPair();
        org.common.models.KeyPair keyPairEntity = org.common.models.KeyPair.builder()
                .publicKey(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()))
                .privateKey(Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()))
                .build();
        keyPairRepository.save(keyPairEntity);
    }

    private KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

}
