package org.posterservice.config.application;

import org.authservice.annotations.ImportFilterJWT;
import org.common.annotations.ScanMainEntitys;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ImportFilterJWT
@ScanMainEntitys
@EnableScheduling
public class ApplicationConfig {
    @Bean
    public FriendShipMapper friendShipMapper() {
        return Mappers.getMapper(FriendShipMapper.class);
    }
}
