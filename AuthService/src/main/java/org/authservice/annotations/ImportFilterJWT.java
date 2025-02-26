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
        org.authservice.filters.JwtAuthFilter.class,
        org.authservice.service.UserService.class,
        org.authservice.service.JwtService.class,
        org.authservice.service.JWTKeyService.class
})
@EnableJpaRepositories(basePackages = "org.authservice.repositories")
public @interface ImportFilterJWT { }
