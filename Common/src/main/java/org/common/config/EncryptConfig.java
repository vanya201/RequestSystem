package org.common.config;

import org.common.utils.AESEncryptor;
import org.common.utils.EncryptUtil;
import org.common.utils.EncryptUtilImpl;
import org.common.utils.Encryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptConfig {

    byte[] key = new byte[16]; //TODO add key

    @Bean
    public Encryptor encryptor() {
        return new AESEncryptor(key);
    }

    @Bean
    public EncryptUtil encryptUtil() {
        return new EncryptUtilImpl(encryptor());
    }
}
