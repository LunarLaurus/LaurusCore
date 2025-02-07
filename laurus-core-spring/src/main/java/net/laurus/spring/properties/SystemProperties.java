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
    private String allowedIp = "127.0.0.1";
    
    private IloProperties ilo = new IloProperties();
    
    private InfluxDBProperties influxdb = new InfluxDBProperties();
    
    @PostConstruct
    void logSetup() {
    	log.info("Configured application using: {}", this);
    }

}
