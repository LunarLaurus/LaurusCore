package net.laurus.spring.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuration properties for iLO (Integrated Lights-Out) authentication and network settings.
 */
@Data
@Slf4j
@ConfigurationProperties(prefix = "ilo")
public class IloProperties {

    /**
     * Default username for iLO access.
     * Should be changed in production environments.
     */
    private String username;

    /**
     * Default password for iLO access.
     * Should be changed in production environments.
     */
    private String password;

    /**
     * Timeout (in milliseconds) for client connection attempts.
     */
    private int clientTimeoutConnect;

    /**
     * Timeout (in milliseconds) for client read operations.
     */
    private int clientTimeoutRead;

    /**
     * Network configuration properties associated with iLO.
     */
    private NetworkProperties network = new NetworkProperties();

    /**
     * Logs system properties after initialization.
     */    
    @PostConstruct
    void logSetup() {
        log.info("Configured Ilo Client using: {}", this);
    }
}
