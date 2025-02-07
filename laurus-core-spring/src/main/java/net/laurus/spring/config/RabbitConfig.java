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

@Configuration
@EnableRabbit
@Slf4j
@RequiredArgsConstructor
public class RabbitConfig {

    private static final int RETRY_ATTEMPTS = 3;
    private static final long BACKOFF_PERIOD_MS = 1000L;

    private final RabbitProperties rabbitProperties;

    @PostConstruct
    public void init() {
        log.info("RabbitMQ Config Initialized with Host: {}, Port: {}", 
                 rabbitProperties.getHost(), rabbitProperties.getPort());
        log.info("Deserialization Whitelist: {}", Arrays.toString(ALLOW_SERIALIZING_CLASSES));
    }

    /**
     * Registers CachingConnectionFactory as a Spring Bean.
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
     * Registers RabbitAdmin as a Spring Bean using the same ConnectionFactory.
     */
    @Bean
    public AmqpAdmin amqpAdmin(CachingConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /**
     * Registers RabbitTemplate as a Spring Bean using the same ConnectionFactory.
     */
    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(allowedListDeserializingMessageConverter());
        return rabbitTemplate;
    }

    /**
     * Allowed List Deserializing Message Converter.
     */
    @Bean
    public AllowedListDeserializingMessageConverter allowedListDeserializingMessageConverter() {
        AllowedListDeserializingMessageConverter converter = new SerializerMessageConverter();
        converter.addAllowedListPatterns(ALLOW_SERIALIZING_CLASSES);
        return converter;
    }

    /**
     * Retry Template for RabbitMQ Operations.
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
