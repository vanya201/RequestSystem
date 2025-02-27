package org.authservice.annotations;

import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({
        org.authservice.service.JwtService.class,
        org.authservice.service.JWTKeyService.class,
        org.authservice.config.cache.KeyCachingConfig.class
})
@EnableJpaRepositories(basePackages = "org.authservice.repositories")
public @interface ImportServiceJWT { }
