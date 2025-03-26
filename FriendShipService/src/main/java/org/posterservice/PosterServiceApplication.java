package org.posterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.posterservice.repositories")
public class PosterServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PosterServiceApplication.class, args);
    }
}
