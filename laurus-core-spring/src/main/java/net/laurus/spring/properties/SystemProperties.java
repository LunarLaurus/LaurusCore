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
     */
    @NonNull
    private String allowedIp;

    /**
     * Logs system properties after initialization.
     */
    @PostConstruct
    void logSetup() {
        log.info("Configured System using: {}", this);
    }
}
