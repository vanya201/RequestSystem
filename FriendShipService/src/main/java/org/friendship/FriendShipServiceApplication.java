package org.friendship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.friendship.repositories")
public class FriendShipServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FriendShipServiceApplication.class, args);
    }
}
