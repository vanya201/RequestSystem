package org.notifyservice.config;


import lombok.RequiredArgsConstructor;
import org.common.annotations.ImportEntities;
import org.common.annotations.ImportJWTFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.friendship.config.rabbit")
@ImportJWTFilter
@RequiredArgsConstructor
@ImportEntities
@ComponentScan(basePackages = {"org.common.converter", "org.common.config"})
public class ApplicationConfig {
}
