package net.laurus.spring.config;

import static net.laurus.rabbit.RabbitConstants.ALLOW_SERIALIZING_CLASSES;

import java.util.Arrays;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AllowedListDeserializingMessageConverter;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.laurus.spring.properties.RabbitProperties;

/**
 * Configuration for RabbitMQ messaging.
 * Handles connection, serialization, and retry policies.
 */
@Configuration
@EnableRabbit
@Slf4j
@RequiredArgsConstructor
public class RabbitConfig {

    private static final int RETRY_ATTEMPTS = 3;
    private static final long BACKOFF_PERIOD_MS = 1000L;

    private final RabbitProperties rabbitProperties;

    /**
     * Logs RabbitMQ configuration settings on startup.
     */
    @PostConstruct
    public void init() {
        log.info("RabbitMQ Config Initialized with Host: {}, Port: {}", 
                 rabbitProperties.getHost(), rabbitProperties.getPort());
        log.info("Deserialization Whitelist: {}", Arrays.toString(ALLOW_SERIALIZING_CLASSES));
    }

    /**
     * Creates and configures the RabbitMQ connection factory.
     *
     * @return the configured {@link CachingConnectionFactory} instance.
     */
    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory(rabbitProperties.getHost());
        factory.setPort(rabbitProperties.getPort());
        factory.setUsername(rabbitProperties.getUsername());
        factory.setPassword(rabbitProperties.getPassword());
        return factory;
    }

    /**
     * Registers an AMQP Admin bean for managing RabbitMQ components.
     *
     * @param connectionFactory The RabbitMQ connection factory.
     * @return an instance of {@link AmqpAdmin}.
     */
    @Bean
    public AmqpAdmin amqpAdmin(CachingConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /**
     * Creates a RabbitTemplate bean for sending messages.
     *
     * @param connectionFactory The RabbitMQ connection factory.
     * @return a configured {@link RabbitTemplate}.
     */
    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(allowedListDeserializingMessageConverter());
        return rabbitTemplate;
    }

    /**
     * Provides an AllowedListDeserializingMessageConverter for secure deserialization.
     *
     * @return an instance of {@link AllowedListDeserializingMessageConverter}.
     */
    @Bean
    public AllowedListDeserializingMessageConverter allowedListDeserializingMessageConverter() {
        AllowedListDeserializingMessageConverter converter = new SerializerMessageConverter();
        converter.addAllowedListPatterns(ALLOW_SERIALIZING_CLASSES);
        return converter;
    }

    /**
     * Defines a retry template for handling failed message operations.
     *
     * @return a configured {@link RetryTemplate}.
     */
    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(RETRY_ATTEMPTS));

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(BACKOFF_PERIOD_MS);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }
}
