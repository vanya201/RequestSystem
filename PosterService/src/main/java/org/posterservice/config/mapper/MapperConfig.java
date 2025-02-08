package org.posterservice.config.mapper;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public FriendRequestMapper friendRequestMapper() {
        return Mappers.getMapper(FriendRequestMapper.class);
    }
}
