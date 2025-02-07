package net.laurus.spring.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NonNull;

/**
 * Configuration properties for RabbitMQ.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitProperties {

    /**
     * RabbitMQ host address.
     */
    @NonNull
    private String host;

    /**
     * RabbitMQ port number.
     */
    private int port;

    /**
     * Username for RabbitMQ authentication.
     */
    @NonNull
    private String username;

    /**
     * Password for RabbitMQ authentication.
     */
    @NonNull
    private String password;

    /**
     * List of allowed serializing classes for message conversion.
     */
    @NonNull
    private List<String> allowedSerializingClasses;
}
