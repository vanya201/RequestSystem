package org.common.annotations;


import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableJpaRepositories(basePackages = "org.common.repository")
@ImportEntities
@Import({
        org.common.config.KeyCacheConfig.class,
        org.common.service.UserService.class,
        org.common.service.CryptoKeyService.class,
        org.common.service.RSAKeyService.class,
        org.common.service.JwtService.class
})
public @interface ImportJWTService {
}
