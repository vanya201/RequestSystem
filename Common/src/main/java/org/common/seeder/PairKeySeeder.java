package org.common.seeder;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.common.models.KeyPairRSA;
import org.common.repository.KeyPairRepository;
import org.common.utils.EncryptUtil;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.time.LocalDateTime;
import java.util.Base64;


@Component
@AllArgsConstructor
public class PairKeySeeder {

    private final KeyPairRepository keyPairRepository;
    private final EncryptUtil encryptUtil;

    @PostConstruct
    public void init() throws Exception {
        generateAndSaveKeyPair();
    }

    private void generateAndSaveKeyPair() throws Exception {
        if (keyPairRepository.findAll().isEmpty()) {
            KeyPair keyPair = generateKeyPair();
            KeyPairRSA keyPairRSAEntity =
                    KeyPairRSA
                    .builder()
                    .publicKey(keyPair.getPublic().getEncoded())
                    .encryptPrivateKey(encryptUtil.encrypt(keyPair.getPrivate().getEncoded()))
                    .build();
            keyPairRepository.save(keyPairRSAEntity);
        }
    }

    private KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
}
