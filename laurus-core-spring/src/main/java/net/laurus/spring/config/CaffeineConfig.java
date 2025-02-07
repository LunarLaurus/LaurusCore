package net.laurus.spring.config;

import static java.util.concurrent.TimeUnit.HOURS;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * Configuration class for Caffeine-based caching.
 */
@Configuration
@EnableCaching
public class CaffeineConfig {

    /**
     * Configures Caffeine cache with an expiration time of 6 hours and a max size of 1000 entries.
     *
     * @return a configured {@link Caffeine} instance.
     */
    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(6, HOURS)
                .maximumSize(1000);
    }

    /**
     * Creates a CaffeineCacheManager bean using the configured Caffeine instance.
     *
     * @param caffeine The Caffeine configuration.
     * @return a {@link CacheManager} instance.
     */
    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}
