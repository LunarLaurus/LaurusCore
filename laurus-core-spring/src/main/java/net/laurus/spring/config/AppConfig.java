package net.laurus.spring.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import net.laurus.thread.LaurusThreadFactory;

/**
 * Application configuration class for setting up essential beans.
 */
@Configuration
public class AppConfig {

    /**
     * Creates a cached thread pool for handling concurrent tasks.
     *
     * @return ExecutorService instance for managing client threads.
     */
    @Bean
    public ExecutorService threadPool() {
        return Executors.newCachedThreadPool(new LaurusThreadFactory("Client-Thread", true));
    }

    /**
     * Configures Jackson's ObjectMapper with indentation and no wrapper usage.
     *
     * @return a configured {@link Jackson2ObjectMapperBuilder} instance.
     */
    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .defaultUseWrapper(false)
                .indentOutput(true);
    }
}
