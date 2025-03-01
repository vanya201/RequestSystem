package org.common.config;

import org.common.utils.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptConfig {

    byte[] key = new byte[16]; //TODO add key

    @Bean
    public ByteEncryptor encryptor() {
        return new AESByteEncryptor(key);
    }

    @Bean
    public ByteEncryptor byteEncryptUtil() {
        return new ByteEncryptUtil(encryptor());
    }

    @Bean
    public StringEncryptor base64StringEncryptUtil() {
        return new Base64StringEncryptUtil(encryptor());
    }

    @Bean
    public ObjectEncryptor objectEncryptUtil() {
        return new ObjectEncryptUtil(encryptor(), new ObjectDataSerializer());
    }
}
