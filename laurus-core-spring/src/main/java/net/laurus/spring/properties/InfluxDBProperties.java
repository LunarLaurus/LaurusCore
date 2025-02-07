package net.laurus.spring.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuration properties for InfluxDB connection.
 */
@Data
@Slf4j
@ConfigurationProperties(prefix = "influx")
public class InfluxDBProperties {

    /**
     * URL of the InfluxDB instance.
     */
    private String url;

    /**
     * Username for InfluxDB authentication.
     */
    private String username;

    /**
     * Password for InfluxDB authentication.
     */
    private String password;

    /**
     * Name of the InfluxDB database.
     */
    private String database;

    /**
     * Logs system properties after initialization.
     */    
    @PostConstruct
    void logSetup() {
        log.info("Configured InfluxDB using: {}", this);
    }
}
