package org.friendship.config.application;


import lombok.RequiredArgsConstructor;
import org.common.annotations.ImportEntities;
import org.common.annotations.ImportJWTFilter;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ImportJWTFilter
@RequiredArgsConstructor
@ImportEntities
@ComponentScan(basePackages = {"org.common.converter", "org.common.config"})
public class ApplicationConfig {
    @Bean
    public FriendShipMapper friendShipMapper() {
        return Mappers.getMapper(FriendShipMapper.class);
    }
}
