package org.posterservice.config.redis;

import org.common.model.FriendRequest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Set;

@Configuration
@EnableCaching
public class FriendRequestRedisConfig {

    @Bean
    public RedisTemplate<String, Set<String>> friendRequestsForUserRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Set<String>> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }



    @Bean
    public RedisTemplate<String, FriendRequest> friendRequestRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, FriendRequest> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<FriendRequest> friendRequestSerializer = new Jackson2JsonRedisSerializer<>(FriendRequest.class);
        template.setValueSerializer(friendRequestSerializer);
        return template;
    }
}

