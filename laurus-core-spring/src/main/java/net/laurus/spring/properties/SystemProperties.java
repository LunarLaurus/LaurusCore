package net.laurus.spring.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@ConfigurationProperties(prefix = "system")
public class SystemProperties {

    @Accessors(fluent = true)
    private boolean obfuscateSecrets;
    @NonNull
    private String allowedIp;
    
    private IloProperties ilo;
    
    private InfluxDBProperties influxdb;
    
    @PostConstruct
    void logSetup() {
    	log.info("Configured application using: {}", this);
    }

}
