package net.laurus.spring.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * System-wide configuration properties.
 */
@Data
@Slf4j
@ConfigurationProperties(prefix = "system")
public class SystemProperties {

    /**
     * Determines whether secrets (e.g., passwords) should be obfuscated in logs.
     */
    @Accessors(fluent = true)
    private boolean obfuscateSecrets;

    /**
     * Allowed IP for system access.
     * Defaults to localhost (127.0.0.1).
     */
    @NonNull
    private String allowedIp = "127.0.0.1";

    /**
     * iLO authentication properties.
     */
    private IloProperties ilo = new IloProperties();

    /**
     * InfluxDB connection properties.
     */
    private InfluxDBProperties influxdb = new InfluxDBProperties();

    /**
     * Logs system properties after initialization.
     */
    @PostConstruct
    void logSetup() {
        log.info("Configured application using: {}", this);
    }
}
