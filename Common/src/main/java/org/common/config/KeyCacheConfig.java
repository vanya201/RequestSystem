package org.common.config;

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
public class KeyCacheConfig {

    public static final String PAIR_KEY_CACHE_NAME = "pairKey";

    @Bean
    public CacheManager cacheManager() {
        Cache cache = new ConcurrentMapCache(PAIR_KEY_CACHE_NAME);
        var manager = new SimpleCacheManager();
        manager.setCaches(Collections.singletonList(cache));
        return manager;
    }
}
