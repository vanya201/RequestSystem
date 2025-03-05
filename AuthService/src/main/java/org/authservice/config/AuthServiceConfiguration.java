package org.authservice.config;

import lombok.RequiredArgsConstructor;
import org.common.annotations.ImportEntities;
import org.common.annotations.ImportJWTFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ImportJWTFilter
@RequiredArgsConstructor
@ImportEntities
@ComponentScan(basePackages = {"org.common.converter", "org.common.config"})
public class AuthServiceConfiguration {

}
