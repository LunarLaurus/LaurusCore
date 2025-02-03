package net.laurus.spring.config;

import static net.laurus.rabbit.RabbitConstants.ALLOW_SERIALIZING_CLASSES;

import java.util.Arrays;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AllowedListDeserializingMessageConverter;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import lombok.extern.java.Log;

@Configuration
@EnableRabbit
@Log
public class RabbitConfigProperties {

	private final String rabbitAddress;
	private final int rabbitPort;
	private final String rabbitUsername;
	private final String rabbitPassword;

	public RabbitConfigProperties(@Value("${spring.rabbitmq.host}") String rabbitAddress,
			@Value("${spring.rabbitmq.port}") int rabbitPort,
			@Value("${spring.rabbitmq.username}") String rabbitUsername,
			@Value("${spring.rabbitmq.password}") String rabbitPassword) {
		this.rabbitAddress = rabbitAddress;
		this.rabbitPort = rabbitPort;
		this.rabbitUsername = rabbitUsername;
		this.rabbitPassword = rabbitPassword;
        log.info("Deserializaation Whitelist: " + Arrays.toString(ALLOW_SERIALIZING_CLASSES));
	}

	@Bean
	public CachingConnectionFactory connectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory(rabbitAddress); // Change to your RabbitMQ host
		factory.setPort(rabbitPort);
		factory.setUsername(rabbitUsername);
		factory.setPassword(rabbitPassword);
		return factory;
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
		rabbitTemplate.setMessageConverter(allowedListDeserializingMessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public AllowedListDeserializingMessageConverter allowedListDeserializingMessageConverter() {
		AllowedListDeserializingMessageConverter converter = new SerializerMessageConverter();
		converter.addAllowedListPatterns(ALLOW_SERIALIZING_CLASSES);
		return converter;
	}
	
	@Bean
	public RetryTemplate retryTemplate() {
	    RetryTemplate retryTemplate = new RetryTemplate();
	    retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3)); // Retry up to 3 times
	    retryTemplate.setBackOffPolicy(new FixedBackOffPolicy()); // Wait 1 second between retries
	    return retryTemplate;
	}
}
