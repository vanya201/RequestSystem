package org.notifyservice.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.posterservice.config.rabbit")
public class ApplicationConfig {
}
