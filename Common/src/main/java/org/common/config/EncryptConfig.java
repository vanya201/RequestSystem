package org.common.config;


import org.common.utils.AESEncryptUtil;
import org.common.utils.EncryptUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptConfig {

    byte[] key = new byte[16]; //TODO add key

    @Bean
    public EncryptUtil encryptUtil() {
        return new AESEncryptUtil(key);
    }
}
