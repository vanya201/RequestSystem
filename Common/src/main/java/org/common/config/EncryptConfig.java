package org.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class EncryptConfig {

    @Value("${encryption.secret-key}")
    private String SECRET_KEY;

    @Value("${encryption.salt}")
    private String SALT;



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public BytesEncryptor byteEncryptor() {
        return new AesBytesEncryptor(SECRET_KEY, SALT);
    }



    @Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.text(SECRET_KEY, SALT);
    }
}
