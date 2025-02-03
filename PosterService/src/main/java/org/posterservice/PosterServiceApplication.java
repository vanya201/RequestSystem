package org.posterservice;

import jakarta.persistence.Entity;
import org.authservice.annotations.ImportFilterJWT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ImportFilterJWT
@EntityScan(basePackages = "org.common.models")
public class PosterServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PosterServiceApplication.class, args);
    }
}
