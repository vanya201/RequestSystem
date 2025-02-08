package org.posterservice;

import org.authservice.annotations.ImportFilterJWT;
import org.common.annotations.ScanMainEntitys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@ImportFilterJWT
@ScanMainEntitys
public class PosterServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PosterServiceApplication.class, args);
    }
}
