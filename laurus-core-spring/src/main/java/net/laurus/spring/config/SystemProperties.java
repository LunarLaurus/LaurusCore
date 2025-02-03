package net.laurus.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Configuration
@ConfigurationProperties(prefix = "system")
@Data
@Slf4j
public class SystemProperties {

    private boolean obfuscateSecrets;
    private String allowedIp;
    private IloProperties ilo;
    private InfluxDBProperties influxdb;
    
    @PostConstruct
    void logSetup() {
    	log.info("Configured application using: {}", this);
    }

}
