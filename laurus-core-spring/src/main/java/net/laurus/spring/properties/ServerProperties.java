package net.laurus.spring.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Configuration properties for the server.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "server")
public class ServerProperties {

    /**
     * Server port number.
     */
    private int port;
}
