package org.authservice.config.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@EnableCaching
public class KeyCachingConfig {
    @Bean
    public CacheManager cacheManager() {
        Cache cache = new ConcurrentMapCache("pairKey");
        var manager = new SimpleCacheManager();
        manager.setCaches(Collections.singletonList(cache));
        return manager;
    }
}
