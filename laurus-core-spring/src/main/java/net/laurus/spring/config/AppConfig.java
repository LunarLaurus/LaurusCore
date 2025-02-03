package net.laurus.spring.config;

import static java.util.concurrent.TimeUnit.HOURS;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.github.benmanes.caffeine.cache.Caffeine;

import net.laurus.thread.LaurusThreadFactory;

@Configuration
@EnableCaching
public class AppConfig {

    @Bean
    public ExecutorService threadPool() {
        return Executors.newCachedThreadPool(new LaurusThreadFactory("Client-Thread", true));
    }

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
        		.defaultUseWrapper(false)
        		.indentOutput(true);
    }
    
    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(6, HOURS)
                .maximumSize(1000);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
    
}
