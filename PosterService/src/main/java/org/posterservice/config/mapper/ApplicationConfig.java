package org.posterservice.config.mapper;

import org.authservice.annotations.ImportFilterJWT;
import org.common.annotations.ScanMainEntitys;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ImportFilterJWT
@ScanMainEntitys
public class ApplicationConfig {
    @Bean
    public FriendShipMapper friendShipMapper() {
        return Mappers.getMapper(FriendShipMapper.class);
    }
}
